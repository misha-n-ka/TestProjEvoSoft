package com.testprojevosoft.anim

import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation

class ShakeError {
    companion object {
        fun shakeError(): TranslateAnimation {
            val shake = TranslateAnimation(0f, 10f, 0f, 0f)
            shake.duration = 500
            shake.interpolator = CycleInterpolator(7f)
            return shake
        }
    }
}