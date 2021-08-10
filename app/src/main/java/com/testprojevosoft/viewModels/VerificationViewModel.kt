package com.testprojevosoft.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.testprojevosoft.Repository
import com.testprojevosoft.Utils.Timer

class VerificationViewModel: ViewModel() {
    private val mRepository = Repository.get()
    lateinit var timer: Timer
    private lateinit var _mutableLiveData: MutableLiveData<Long>
    val remainingSeconds: LiveData<Long>
        get() = _mutableLiveData

    fun isCodeValid(code: String): Boolean {
        val codes =  mRepository.getSmsCodes()
        return codes.firstOrNull{it == code} != null
    }

    fun startTimer (secondsTimer: Int, secondsInterval: Float) {
        val timeMillis = (secondsTimer * 1000).toLong()
        val intervalMillis = (secondsInterval * 1000).toLong()
        timer = Timer(timeMillis, intervalMillis).start() as Timer
        _mutableLiveData.observe(
            Observer(

            )
            )
        timer.isTimerRunning = true
    }
}