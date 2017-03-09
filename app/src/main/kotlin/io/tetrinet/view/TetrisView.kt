package io.tetrinet.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import io.tetrinet.R
import io.tetrinet.engine.TetrisEngine
import io.tetrinet.extension.pick
import io.tetrinet.listener.TetrisSwipeGestureListener
import io.tetrinet.model.Tetrimino
import io.tetrinet.model.TetriminoData
import io.tetrinet.renderer.TetriminoRenderer
import java.util.Random

class TetrisView @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
	interface TetrisEventListener {
		fun onGameOver()

		fun onLinesRemoved(count: Int)

		fun onNextTetriminoChanged(nextTetrimino: Tetrimino)
	}

	var eventListener: TetrisEventListener? = null
	var tetriminoRenderer = TetriminoRenderer(this)
	var tetrisEngine = TetrisEngine()

	private val currentTetrimino by lazy {
		TetriminoData(0, 0, 0, Tetrimino.I).also {
			it.resetForTetrimino(this.nextTetrimino)
			this.nextTetrimino = this.random.pick(Tetrimino.values())

			this.eventListener?.onNextTetriminoChanged(this.nextTetrimino)
		}
	}
	private val gestureDetector by lazy { GestureDetector(this.context, TetrisSwipeGestureListener(this.currentTetrimino)) }
	private var nextTetrimino: Tetrimino
	private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
	private val random = Random()
	private val state = Array(ROWS_COUNT) { IntArray(COLUMNS_COUNT) }

	val columnWidth
		get() = this.measuredWidth.toFloat() / COLUMNS_COUNT

	val rowHeight
		get() = this.measuredHeight.toFloat() / ROWS_COUNT

	init {
		this.nextTetrimino = this.random.pick(Tetrimino.values())
	}

	override fun onTouchEvent(event: MotionEvent?): Boolean {
		return this.gestureDetector.onTouchEvent(event)
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)

		this.drawGrid(canvas)
		this.drawGame(canvas)

		val (status, removedLines) = this.tetrisEngine.loop(this.currentTetrimino, this.state)
		when (status) {
			TetrisEngine.Status.CONTINUE -> this.postInvalidateDelayed(io.tetrinet.view.TetrisView.REFRESH_INTERVAL)
			TetrisEngine.Status.GAME_OVER -> Unit
			TetrisEngine.Status.NEW_TETRIMINO -> {
				this.resetCurrentTetrimino()
				this.postInvalidateDelayed(io.tetrinet.view.TetrisView.REFRESH_INTERVAL)
			}
		}

		if (removedLines > 0) {
			this.eventListener?.onLinesRemoved(removedLines)
		}
	}

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val height = MeasureSpec.getSize(heightMeasureSpec)
		val width = MeasureSpec.getSize(widthMeasureSpec)
		val desiredHeight = width * ROWS_COUNT / COLUMNS_COUNT
		val desiredWidth = height * COLUMNS_COUNT / ROWS_COUNT

		when {
			desiredWidth < width -> this.setMeasuredDimension(desiredWidth, height)
			desiredHeight < height -> this.setMeasuredDimension(width, desiredHeight)
			else -> throw IllegalStateException("TetrisView was not computed properly")
		}
	}

	private fun drawGame(canvas: Canvas) {
		this.state.forEachIndexed { index, row ->
			row.forEachIndexed { column, color ->
				if (color > 0) {
					this.tetriminoRenderer.drawSquare(canvas, index, column, color)
				}
			}
		}

		this.tetriminoRenderer.render(this.currentTetrimino, canvas)
	}

	private fun drawGrid(canvas: Canvas) {
		val colors = arrayOf(R.color.grid_even, R.color.grid_odd).map { ContextCompat.getColor(this.context, it) }

		for (i in 0 until COLUMNS_COUNT) {
			this.paint.color = colors[i % colors.size]

			canvas.drawRect(i * this.columnWidth, 0f, (i + 1) * this.columnWidth, this.measuredHeight.toFloat(), this.paint)
		}
	}

	private fun resetCurrentTetrimino() {
		this.currentTetrimino.resetForTetrimino(this.nextTetrimino)
		this.nextTetrimino = this.random.pick(Tetrimino.values())

		this.eventListener?.onNextTetriminoChanged(this.nextTetrimino)
	}

	private fun restartGame() {
		this.resetCurrentTetrimino()

		this.state.forEachIndexed { index, row ->
			row.forEachIndexed { column, _ ->
				this.state[index][column] = 0
			}
		}
	}

	companion object {
		const val COLUMNS_COUNT = 10
		const val ROWS_COUNT = 15

		private const val REFRESH_INTERVAL = 16L
	}
}
