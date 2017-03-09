package io.tetrinet.listener

import android.view.MotionEvent
import io.tetrinet.model.TetriminoData
import io.tetrinet.view.TetrisView
import java.lang.Math.max
import java.lang.Math.min

class TetrisSwipeGestureListener(private val tetriminoData: TetriminoData) : SwipeGestureListener() {
	override fun onSingleTapUp(e: MotionEvent?): Boolean {
		this.tetriminoData.rotate()

		return true
	}

	override fun onSwipeDown(): Boolean {
		this.tetriminoData.row = min(this.tetriminoData.row + 1, TetrisView.ROWS_COUNT - this.tetriminoData.height - 1)

		return true
	}

	override fun onSwipeLeft(): Boolean {
		this.tetriminoData.column = max(this.tetriminoData.column - 1, 0)

		return true
	}

	override fun onSwipeRight(): Boolean {
		this.tetriminoData.column = min(this.tetriminoData.column + 1, TetrisView.COLUMNS_COUNT - this.tetriminoData.width)

		return true
	}
}
