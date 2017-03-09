package io.tetrinet.renderer

import android.graphics.Canvas
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.tetrinet.MainActivity
import io.tetrinet.model.Tetrimino
import io.tetrinet.model.TetriminoData
import io.tetrinet.view.TetrisView
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.spy
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class TetriminoRendererTest {
	@JvmField
	@Rule
	val activityRule = ActivityTestRule(MainActivity::class.java)

	private lateinit var canvas: Canvas
	private lateinit var renderer: TetriminoRenderer
	private lateinit var tetrisView: TetrisView

	@Before
	fun before() {
		this.canvas = Canvas()
		this.tetrisView = TetrisView(this.activityRule.activity)
		this.renderer = spy(TetriminoRenderer(this.tetrisView))
	}

	@Test
	fun render() {
		val data = TetriminoData(0, 5, 4, Tetrimino.T)

		this.renderer.render(data, this.canvas)

		val argumentColumn = ArgumentCaptor.forClass(Int::class.java)
		val argumentRow = ArgumentCaptor.forClass(Int::class.java)

		verify(this.renderer, times(4)).drawSquare(eq(this.canvas), argumentRow.capture(), argumentColumn.capture(), eq(data.color))

		assertThat(argumentColumn.allValues).containsExactly(5, 6, 7, 6)
		assertThat(argumentRow.allValues).containsExactly(4, 4, 4, 5)
	}
}

private fun <T> eq(`object`: T): T {
	Mockito.eq(`object`)
	return `object`
}
