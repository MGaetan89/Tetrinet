package io.tetrinet

import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.TextView
import io.tetrinet.model.Tetrimino
import io.tetrinet.view.TetrisView
import kotlin.properties.Delegates

class MainActivity : Activity(), TetrisView.TetrisEventListener {
	private var lines: TextView? = null
	private var nextTetrimino: TextView? = null
	private var totalRemovedLines by Delegates.observable(0) { _, _, newValue ->
		this.lines?.text = this.resources.getQuantityString(R.plurals.lines, newValue, newValue)
	}

	override fun onGameOver() {
		this.totalRemovedLines = 0
	}

	override fun onLinesRemoved(count: Int) {
		this.totalRemovedLines += count
	}

	override fun onNextTetriminoChanged(nextTetrimino: Tetrimino) {
		this.nextTetrimino?.let {
			it.text = nextTetrimino.name
			it.setTextColor(ContextCompat.getColor(this, nextTetrimino.color))
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		this.setContentView(R.layout.activity_main)

		this.lines = this.findViewById(R.id.lines) as TextView
		this.nextTetrimino = this.findViewById(R.id.next) as TextView

		val tetris = this.findViewById(R.id.tetris) as TetrisView
		tetris.eventListener = this

		this.totalRemovedLines = 0
	}
}
