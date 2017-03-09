package io.tetrinet.model

import android.support.annotation.ColorRes
import io.tetrinet.R

enum class Tetrimino(@ColorRes val color: Int, val structure: Array<BooleanArray>) {
	I(R.color.tetrimino_i, arrayOf(
			booleanArrayOf(true, true, true, true)
	)),

	J(R.color.tetrimino_j, arrayOf(
			booleanArrayOf(true, true, true),
			booleanArrayOf(false, false, true)
	)),

	L(R.color.tetrimino_l, arrayOf(
			booleanArrayOf(true, true, true),
			booleanArrayOf(true, false, false)
	)),

	O(R.color.tetrimino_o, arrayOf(
			booleanArrayOf(true, true),
			booleanArrayOf(true, true)
	)),

	S(R.color.tetrimino_s, arrayOf(
			booleanArrayOf(false, true, true),
			booleanArrayOf(true, true, false)
	)),

	T(R.color.tetrimino_t, arrayOf(
			booleanArrayOf(true, true, true),
			booleanArrayOf(false, true, false)
	)),

	Z(R.color.tetrimino_z, arrayOf(
			booleanArrayOf(true, true, false),
			booleanArrayOf(false, true, true)
	))
}
