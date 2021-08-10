package com.testprojevosoft.viewModels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testprojevosoft.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class VerificationViewModel: ViewModel() {

    private val mRepository = Repository.get()
    private lateinit var timer: CountDownTimer

    private val _remainingSeconds = MutableLiveData<String>()
    val remainingSecondsLiveData: LiveData<String>
        get() = _remainingSeconds

    private val _isTimerElapsed = MutableLiveData<Boolean>()
    val isTimerElapsed: LiveData<Boolean>
        get() = _isTimerElapsed


    fun isCodeValid(code: String): Boolean {
        var codes: List<String> = emptyList()

        viewModelScope.launch {
            val job = async { mRepository.getSmsCodes()}
            codes = job.await()
        }
        return codes.firstOrNull{it == code} != null
    }

    suspend fun retryVerificationCode() {
        mRepository.requestVerificationCode()

    }

    fun startTimer (secondsTimer: Int, secondsInterval: Float) {
        val millisIntFuture = (secondsTimer * 1000).toLong()
        val countDownInterval = (secondsInterval * 1000).toLong()
        _isTimerElapsed.value = false

        timer = object : CountDownTimer(millisIntFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                _remainingSeconds.value = (millisUntilFinished / 1000).toString()
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