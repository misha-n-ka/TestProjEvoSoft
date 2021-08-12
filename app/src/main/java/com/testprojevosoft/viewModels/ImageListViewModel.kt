package com.testprojevosoft.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testprojevosoft.Repository
import kotlinx.coroutines.*

class ImageListViewModel : ViewModel() {

    private val mRepository = Repository.get()

    suspend fun getNextImages(numLoadImages: Int): List<String> {
        return mRepository.getNextImages(numLoadImages)
    }
}