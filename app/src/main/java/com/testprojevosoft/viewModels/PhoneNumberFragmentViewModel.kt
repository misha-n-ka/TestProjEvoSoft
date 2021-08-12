package com.testprojevosoft.viewModels

import androidx.lifecycle.ViewModel
import com.testprojevosoft.Repository

class PhoneNumberFragmentViewModel: ViewModel() {

    private val mRepository = Repository.get()

    // check if number is in database
    fun isNumberInBase(number: String): Boolean {
        val numbers = mRepository.getPhoneNumbers()
        return numbers.firstOrNull { it == number } != null
    }

    suspend fun requestVerificationCode() {
        mRepository.requestVerificationCode()
    }
}