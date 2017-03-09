package io.tetrinet.renderer

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import io.tetrinet.model.TetriminoData
import io.tetrinet.view.TetrisView

open class TetriminoRenderer(private val tetrisView: TetrisView) {
	private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
	private val rect = RectF()

	fun render(tetriminoData: TetriminoData, canvas: Canvas) {
		val color = tetriminoData.color
		val x = tetriminoData.column
		val y = tetriminoData.row

		tetriminoData.loopStructure { row, column, occupied ->
			if (occupied) {
				this.drawSquare(canvas, y + row, x + column, color)
			}
		}
	}

	// Open for testing
	open fun drawSquare(canvas: Canvas, row: Int, column: Int, @ColorRes color: Int) {
		val left = column * this.tetrisView.columnWidth
		val right = left + this.tetrisView.columnWidth
		val top = row * this.tetrisView.rowHeight
		val bottom = top + this.tetrisView.rowHeight
		val colorInt = ContextCompat.getColor(this.tetrisView.context, color)

		this.drawSquare(canvas, left, top, right, bottom, colorInt)
	}

	open fun drawSquare(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float, @ColorInt color: Int) {
		this.paint.color = color

		this.rect.set(left + BORDER_WIDTH, top + BORDER_WIDTH, right - 2f * BORDER_WIDTH, bottom - 2f * BORDER_WIDTH)

		canvas.drawRoundRect(this.rect, BORDER_RADIUS, BORDER_RADIUS, this.paint)
	}

	companion object {
		private const val BORDER_RADIUS = 8f
		private const val BORDER_WIDTH = 1f
	}
}
