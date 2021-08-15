package com.testprojevosoft.viewModels

import androidx.lifecycle.ViewModel
import com.testprojevosoft.Repository

class PhoneNumberFragmentViewModel: ViewModel() {

    private val mRepository = Repository.get()

    suspend fun requestVerificationCode(number: String): Boolean {
        return mRepository.requestVerificationCode(number)
    }
}