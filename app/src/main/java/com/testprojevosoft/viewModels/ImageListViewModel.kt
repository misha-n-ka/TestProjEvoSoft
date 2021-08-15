package com.testprojevosoft.viewModels

import androidx.lifecycle.ViewModel
import com.testprojevosoft.Repository

class ImageListViewModel : ViewModel() {

    private val mRepository = Repository.get()

    suspend fun getNextImages(numLoadImages: Int): List<String> {
        return mRepository.getNextImages(numLoadImages)
    }
}