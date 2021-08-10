package com.testprojevosoft.viewModels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testprojevosoft.Repository

class VerificationViewModel: ViewModel() {

    private val mRepository = Repository.get()
    private lateinit var timer: CountDownTimer

    private lateinit var _remainingSeconds: MutableLiveData<Long>
    val remainingSecondsLiveData: LiveData<Long>
        get() = _remainingSeconds

    private lateinit var _isTimerElapsed: MutableLiveData<Boolean>
    val isTimerRunning: LiveData<Boolean>
        get() = _isTimerElapsed

    fun isCodeValid(code: String): Boolean {
        val codes =  mRepository.getSmsCodes()
        return codes.firstOrNull{it == code} != null
    }

    fun retryVerificationCode() {
        mRepository.requestVerificationCode()
    }

    fun startTimer (secondsTimer: Int, secondsInterval: Float) {
        val millisIntFuture = (secondsTimer * 1000).toLong()
        val countDownInterval = (secondsInterval * 1000).toLong()
        _isTimerElapsed.value = false

        timer = object : CountDownTimer(millisIntFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                _remainingSeconds.value = (millisIntFuture / 1000)
            }

            override fun onFinish() {
                _isTimerElapsed.value = true
            }
        }.start()
    }

    fun cancelTimer() {
        timer.cancel()
    }
}