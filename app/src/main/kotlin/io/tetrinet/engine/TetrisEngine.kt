package io.tetrinet.engine

import android.support.annotation.VisibleForTesting
import io.tetrinet.model.TetriminoData
import io.tetrinet.view.TetrisView

open class TetrisEngine {
	private var lastMovementTime = 0L

	open fun loop(tetriminoData: TetriminoData, state: Array<IntArray>): Result {
		val status = this.getGameStatus(tetriminoData, state)

		this.updateGame(status, tetriminoData, state)

		val removedLines = this.removeFullLines(state)

		return Result(status, removedLines)
	}

	@VisibleForTesting
	internal fun getGameStatus(tetriminoData: TetriminoData, state: Array<IntArray>): Status {
		return if (this.isOnLastRow(tetriminoData)) {
			Status.NEW_TETRIMINO
		} else if (this.isBlockedByNextRow(tetriminoData, state)) {
			if (tetriminoData.row == 0) {
				Status.GAME_OVER
			} else {
				Status.NEW_TETRIMINO
			}
		} else {
			Status.CONTINUE
		}
	}

	private fun isBlockedByNextRow(tetriminoData: TetriminoData, state: Array<IntArray>): Boolean {
		var isBlocked = false

		tetriminoData.loopStructure { row, column, occupied ->
			if (occupied && state[tetriminoData.row + row + 1][tetriminoData.column + column] > 0) {
				isBlocked = true

				return@loopStructure
			}
		}

		return isBlocked
	}

	@VisibleForTesting
	internal fun isOnLastRow(tetriminoData: TetriminoData): Boolean {
		return tetriminoData.row + tetriminoData.height >= TetrisView.ROWS_COUNT
	}

	private fun removeFullLines(state: Array<IntArray>): Int {
		var removedLines = 0
		state.forEachIndexed { index, row ->
			if (row.count { it == 0 } == 0) {
				removedLines++

				for (i in index downTo 1) {
					state[i] = state[i - 1]
				}
			}
		}

		return removedLines
	}

	private fun updateGame(status: Status, tetriminoData: TetriminoData, state: Array<IntArray>) {
		when (status) {
			Status.CONTINUE -> {
				if (System.currentTimeMillis() - this.lastMovementTime > MOVEMENT_TIME) {
					tetriminoData.row++

					this.lastMovementTime = System.currentTimeMillis()
				}
			}

			Status.GAME_OVER, Status.NEW_TETRIMINO -> tetriminoData.loopStructure { row, column, occupied ->
				if (occupied) {
					state[tetriminoData.row + row][tetriminoData.column + column] = tetriminoData.color
				}
			}
		}
	}

	data class Result(val status: Status, val removedLines: Int)

	enum class Status {
		CONTINUE, GAME_OVER, NEW_TETRIMINO
	}

	companion object {
		private const val MOVEMENT_TIME = 500L
	}
}
