package com.testprojevosoft.viewModels

import androidx.lifecycle.ViewModel
import com.testprojevosoft.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class PhoneNumberFragmentViewModel: ViewModel() {

    private val mRepository = Repository.get()

    fun isNumberInBase(number: String): Boolean {
        val numbers = mRepository.getPhoneNumbers()
        return numbers.firstOrNull { it == number } != null
    }

    fun requestVerificationCode() {

        Repository.requestVerificationCode()
    }

}