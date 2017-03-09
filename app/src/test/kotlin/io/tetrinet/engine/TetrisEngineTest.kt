package io.tetrinet.engine

import io.tetrinet.model.Tetrimino
import io.tetrinet.model.TetriminoData
import io.tetrinet.view.TetrisView
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class TetrisEngineTest {
	private lateinit var engine: TetrisEngine

	@Before
	fun before() {
		this.engine = TetrisEngine()
	}

	@Test
	fun getGameStatus() {
		val state = arrayOf<IntArray>()

		// Last row
		val data = TetriminoData(0, 0, TetrisView.Companion.ROWS_COUNT, Tetrimino.T)

		assertThat(this.engine.getGameStatus(data, state)).isEqualTo(TetrisEngine.Status.NEW_TETRIMINO)

		// Blocked on first row
		// TODO

		// Blocked in game
		// TODO

		// Nothing to report
		// TODO
	}

	@Test
	fun isOnLastRow() {
		val data = TetriminoData(90, 0, 0, Tetrimino.T)

		assertThat(this.engine.isOnLastRow(data)).isFalse()

		data.row = TetrisView.ROWS_COUNT - data.height - 1

		assertThat(this.engine.isOnLastRow(data)).isFalse()

		data.row++

		assertThat(this.engine.isOnLastRow(data)).isTrue()

		data.row++

		assertThat(this.engine.isOnLastRow(data)).isTrue()
	}
}
