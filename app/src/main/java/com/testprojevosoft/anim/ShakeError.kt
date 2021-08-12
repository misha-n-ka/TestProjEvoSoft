package com.testprojevosoft.anim

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.testprojevosoft.R

class ShakeError {

    companion object {
        // shake animation builder for error action in validation
        fun shakeError(): TranslateAnimation {
            val shake = TranslateAnimation(0f, 10f, 0f, 0f)
            shake.duration = durationAnimation
            shake.interpolator = CycleInterpolator(7f)
            return shake
        }
        private const val durationAnimation = 500L
    }
}