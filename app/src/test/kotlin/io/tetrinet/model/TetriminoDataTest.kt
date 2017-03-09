package io.tetrinet.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TetriminoDataTest {
	@Test
	fun color() {
		Tetrimino.values().forEach {
			val data = TetriminoData(0, 0, 0, it)

			assertThat(data.color)
					.`as`("checking %s's color", it.name)
					.isEqualTo(it.color)
		}
	}

	@Test
	fun getRotatedRowAndColumn() {
		val data = TetriminoData(0, 0, 0, Tetrimino.T)

		// Angle = 0
		data.getRotatedRowAndColumn(0, 0).let {
			assertThat(it[0]).isEqualTo(0)
			assertThat(it[1]).isEqualTo(0)
		}
		data.getRotatedRowAndColumn(0, 1).let {
			assertThat(it[0]).isEqualTo(0)
			assertThat(it[1]).isEqualTo(1)
		}
		data.getRotatedRowAndColumn(0, 2).let {
			assertThat(it[0]).isEqualTo(0)
			assertThat(it[1]).isEqualTo(2)
		}
		data.getRotatedRowAndColumn(1, 0).let {
			assertThat(it[0]).isEqualTo(1)
			assertThat(it[1]).isEqualTo(0)
		}
		data.getRotatedRowAndColumn(1, 1).let {
			assertThat(it[0]).isEqualTo(1)
			assertThat(it[1]).isEqualTo(1)
		}
		data.getRotatedRowAndColumn(1, 2).let {
			assertThat(it[0]).isEqualTo(1)
			assertThat(it[1]).isEqualTo(2)
		}

		// Angle = 90
		data.rotate()
		data.getRotatedRowAndColumn(0, 0).let {
			assertThat(it[0]).isEqualTo(0)
			assertThat(it[1]).isEqualTo(1)
		}
		data.getRotatedRowAndColumn(0, 1).let {
			assertThat(it[0]).isEqualTo(1)
			assertThat(it[1]).isEqualTo(1)
		}
		data.getRotatedRowAndColumn(0, 2).let {
			assertThat(it[0]).isEqualTo(2)
			assertThat(it[1]).isEqualTo(1)
		}
		data.getRotatedRowAndColumn(1, 0).let {
			assertThat(it[0]).isEqualTo(0)
			assertThat(it[1]).isEqualTo(0)
		}
		data.getRotatedRowAndColumn(1, 1).let {
			assertThat(it[0]).isEqualTo(1)
			assertThat(it[1]).isEqualTo(0)
		}
		data.getRotatedRowAndColumn(1, 2).let {
			assertThat(it[0]).isEqualTo(2)
			assertThat(it[1]).isEqualTo(0)
		}

		// Angle = 180
		data.rotate()
		data.getRotatedRowAndColumn(0, 0).let {
			assertThat(it[0]).isEqualTo(1)
			assertThat(it[1]).isEqualTo(2)
		}
		data.getRotatedRowAndColumn(0, 1).let {
			assertThat(it[0]).isEqualTo(1)
			assertThat(it[1]).isEqualTo(1)
		}
		data.getRotatedRowAndColumn(0, 2).let {
			assertThat(it[0]).isEqualTo(1)
			assertThat(it[1]).isEqualTo(0)
		}
		data.getRotatedRowAndColumn(1, 0).let {
			assertThat(it[0]).isEqualTo(0)
			assertThat(it[1]).isEqualTo(2)
		}
		data.getRotatedRowAndColumn(1, 1).let {
			assertThat(it[0]).isEqualTo(0)
			assertThat(it[1]).isEqualTo(1)
		}
		data.getRotatedRowAndColumn(1, 2).let {
			assertThat(it[0]).isEqualTo(0)
			assertThat(it[1]).isEqualTo(0)
		}

		// Angle = 270
		data.rotate()
		data.getRotatedRowAndColumn(0, 0).let {
			assertThat(it[0]).isEqualTo(2)
			assertThat(it[1]).isEqualTo(0)
		}
		data.getRotatedRowAndColumn(0, 1).let {
			assertThat(it[0]).isEqualTo(1)
			assertThat(it[1]).isEqualTo(0)
		}
		data.getRotatedRowAndColumn(0, 2).let {
			assertThat(it[0]).isEqualTo(0)
			assertThat(it[1]).isEqualTo(0)
		}
		data.getRotatedRowAndColumn(1, 0).let {
			assertThat(it[0]).isEqualTo(2)
			assertThat(it[1]).isEqualTo(1)
		}
		data.getRotatedRowAndColumn(1, 1).let {
			assertThat(it[0]).isEqualTo(1)
			assertThat(it[1]).isEqualTo(1)
		}
		data.getRotatedRowAndColumn(1, 2).let {
			assertThat(it[0]).isEqualTo(0)
			assertThat(it[1]).isEqualTo(1)
		}
	}

	@Test(expected = IllegalStateException::class)
	fun getRotatedRowAndColumnInvalidAngle() {
		val data = TetriminoData(42, 0, 0, Tetrimino.T)
		data.getRotatedRowAndColumn(0, 0)
	}

	@Test
	fun height() {
		val tetrimino = Tetrimino.I

		mapOf(
				0 to tetrimino.structure.size,
				90 to tetrimino.structure[0].size,
				180 to tetrimino.structure.size,
				270 to tetrimino.structure[0].size
		).forEach { angle, height ->
			val data = TetriminoData(angle, 0, 0, tetrimino)

			assertThat(data.height)
					.`as`("checking %s's height at %d°", tetrimino.name, angle)
					.isEqualTo(height)
		}
	}

	@Test
	fun loopStructure() {
		val data = TetriminoData(90, 0, 0, Tetrimino.T)
		val calls = mutableListOf<Triple<Int, Int, Boolean>>()
		val callback: (Int, Int, Boolean) -> Unit = { row, column, occupied ->
			calls.add(Triple(row, column, occupied))
		}

		data.loopStructure(callback)

		assertThat(calls).containsExactly(
				Triple(0, 1, true),
				Triple(1, 1, true),
				Triple(2, 1, true),
				Triple(0, 0, false),
				Triple(1, 0, true),
				Triple(2, 0, false)
		)
	}

	@Test
	fun resetForTetrimino() {
		val data = TetriminoData(90, 5, 3, Tetrimino.I)

		assertThat(data.angle).isEqualTo(90)
		assertThat(data.column).isEqualTo(5)
		assertThat(data.row).isEqualTo(3)
		assertThat(data.tetrimino).isEqualTo(Tetrimino.I)

		data.resetForTetrimino(Tetrimino.O)

		assertThat(data.angle).isEqualTo(0)
		assertThat(data.column).isEqualTo(4)
		assertThat(data.row).isEqualTo(0)
		assertThat(data.tetrimino).isEqualTo(Tetrimino.O)
	}

	@Test
	fun rotate() {
		val data = TetriminoData(0, 0, 0, Tetrimino.I)

		assertThat(data.angle).isEqualTo(0)

		arrayOf(90, 180, 270, 0, 90, 180, 270).forEach {
			data.rotate()

			assertThat(data.angle).isEqualTo(it)
		}
	}

	@Test
	fun width() {
		val tetrimino = Tetrimino.I

		mapOf(
				0 to tetrimino.structure[0].size,
				90 to tetrimino.structure.size,
				180 to tetrimino.structure[0].size,
				270 to tetrimino.structure.size
		).forEach { angle, width ->
			val data = TetriminoData(angle, 0, 0, tetrimino)

			assertThat(data.width)
					.`as`("checking %s's width at %d°", tetrimino.name, angle)
					.isEqualTo(width)
		}
	}
}
