package io.tetrinet.listener

import io.tetrinet.model.Tetrimino
import io.tetrinet.model.TetriminoData
import io.tetrinet.view.TetrisView
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TetrisSwipeGestureListenerTest {
	@Test
	fun onSingleTapUp() {
		val data = TetriminoData(0, 3, 5, Tetrimino.T)
		val listener = TetrisSwipeGestureListener(data)

		arrayOf(90, 180, 270, 0, 90, 180, 270).forEach {
			listener.onSingleTapUp(null)

			assertThat(data.angle).isEqualTo(it)
			assertThat(data.column).isEqualTo(3)
			assertThat(data.row).isEqualTo(5)
		}
	}

	@Test
	fun onSwipeDown() {
		val tetrimino = Tetrimino.T
		val tetriminoHeight = tetrimino.structure.size
		val data = TetriminoData(0, 3, TetrisView.Companion.ROWS_COUNT - tetriminoHeight - 2, tetrimino)
		val listener = TetrisSwipeGestureListener(data)

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(3)
		assertThat(data.row).isEqualTo(TetrisView.ROWS_COUNT - tetriminoHeight - 2)

		listener.onSwipeDown()

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(3)
		assertThat(data.row).isEqualTo(TetrisView.ROWS_COUNT - tetriminoHeight - 1)

		listener.onSwipeDown()

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(3)
		assertThat(data.row).isEqualTo(TetrisView.ROWS_COUNT - tetriminoHeight - 1)
	}

	@Test
	fun onSwipeLeft() {
		val data = TetriminoData(0, 2, 5, Tetrimino.T)
		val listener = TetrisSwipeGestureListener(data)

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(2)
		assertThat(data.row).isEqualTo(5)

		listener.onSwipeLeft()

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(1)
		assertThat(data.row).isEqualTo(5)

		listener.onSwipeLeft()

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(0)
		assertThat(data.row).isEqualTo(5)

		listener.onSwipeLeft()

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(0)
		assertThat(data.row).isEqualTo(5)
	}

	@Test
	fun onSwipeRight() {
		val tetrimino = Tetrimino.T
		val tetriminoWidth = tetrimino.structure[0].size
		val data = TetriminoData(0, TetrisView.Companion.COLUMNS_COUNT - tetriminoWidth - 2, 5, tetrimino)
		val listener = TetrisSwipeGestureListener(data)

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(TetrisView.COLUMNS_COUNT - tetriminoWidth - 2)
		assertThat(data.row).isEqualTo(5)

		listener.onSwipeRight()

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(TetrisView.COLUMNS_COUNT - tetriminoWidth - 1)
		assertThat(data.row).isEqualTo(5)

		listener.onSwipeRight()

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(TetrisView.COLUMNS_COUNT - tetriminoWidth)
		assertThat(data.row).isEqualTo(5)

		listener.onSwipeRight()

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(TetrisView.COLUMNS_COUNT - tetriminoWidth)
		assertThat(data.row).isEqualTo(5)
	}
}
