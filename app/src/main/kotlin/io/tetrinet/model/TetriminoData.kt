package io.tetrinet.model

import android.support.annotation.ColorRes
import android.support.annotation.Size
import android.support.annotation.VisibleForTesting
import io.tetrinet.view.TetrisView

data class TetriminoData(
		@VisibleForTesting internal var angle: Int,
		var column: Int,
		var row: Int,
		@VisibleForTesting internal var tetrimino: Tetrimino
) {
	val color
		@ColorRes
		get() = this.tetrimino.color

	val height
		get() = if (this.angle == 0 || this.angle == 180) this.tetrimino.structure.size else this.tetrimino.structure[0].size

	val width
		get() = if (this.angle == 0 || this.angle == 180) this.tetrimino.structure[0].size else this.tetrimino.structure.size

	fun loopStructure(callback: (Int, Int, Boolean) -> Unit) {
		this.tetrimino.structure.forEachIndexed { row, columns ->
			columns.forEachIndexed { column, state ->
				val (rotatedRow, rotatedColumn) = this.getRotatedRowAndColumn(row, column)

				callback(rotatedRow, rotatedColumn, state)
			}
		}
	}

	fun resetForTetrimino(tetrimino: Tetrimino) {
		this.angle = 0
		this.column = (TetrisView.COLUMNS_COUNT - tetrimino.structure[0].size) / 2
		this.row = 0
		this.tetrimino = tetrimino
	}

	fun rotate() {
		this.angle = (this.angle + ANGLE_INCREMENT) % ANGLE_MAX_VALUE
	}

	@Size(2)
	@VisibleForTesting
	internal fun getRotatedRowAndColumn(row: Int, column: Int) = when (this.angle) {
		0 -> intArrayOf(row, column)
		90 -> intArrayOf(column, this.tetrimino.structure.size - row - 1)
		180 -> intArrayOf(this.tetrimino.structure.size - row - 1, this.tetrimino.structure[0].size - column - 1)
		270 -> intArrayOf(this.tetrimino.structure[0].size - column - 1, row)
		else -> throw IllegalStateException("Invalid angle '${this.angle}'")
	}

	companion object {
		private const val ANGLE_INCREMENT = 90
		private const val ANGLE_MAX_VALUE = 360
	}
}
