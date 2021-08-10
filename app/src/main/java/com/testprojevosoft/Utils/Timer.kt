package com.testprojevosoft.Utils

import android.os.CountDownTimer
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Timer(millis: Long, interval: Long) :
    CountDownTimer(millis, interval) {

    var isTimerRunning: Boolean = false

    private lateinit var _remainingSeconds: MutableLiveData<String>
     val remainingSeconds: LiveData<String>
        get() = _remainingSeconds

    override fun onTick(millisUntilFinished: Long) {
        _remainingSeconds.value = millisUntilFinished.toString()
    }

    override fun onFinish() {
        isTimerRunning = false
    }
}