package io.tetrinet.listener

import android.view.GestureDetector
import android.view.MotionEvent
import java.lang.Math.abs

open class SwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
	override fun onDown(e: MotionEvent) = true

	override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
		val diffX = e2.x - e1.x
		val diffY = e2.y - e1.y

		if (abs(diffX) > abs(diffY)) {
			if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
				return if (diffX > 0f) {
					this.onSwipeRight()
				} else {
					this.onSwipeLeft()
				}
			}
		} else {
			if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
				return if (diffY > 0f) {
					this.onSwipeDown()
				} else {
					this.onSwipeUp()
				}
			}
		}

		return false
	}

	open fun onSwipeDown() = false

	open fun onSwipeLeft() = false

	open fun onSwipeRight() = false

	open fun onSwipeUp() = false

	companion object {
		private const val SWIPE_THRESHOLD = 75f
		private const val SWIPE_VELOCITY_THRESHOLD = 75f
	}
}
