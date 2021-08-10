package com.testprojevosoft

import android.content.Context
import com.testprojevosoft.data.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class Repository {
    fun getPhoneNumbers(): List<String> {
        return Database.phoneNumbers
    }

    suspend fun getSmsCodes(): List<String> {
        delay(2000L)
        return Database.smsCodes
    }

    suspend fun requestVerificationCode() {
        delay(2000L)
    }

    companion object {
        private var INSTANCE: Repository? = null

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = Repository()
            }
        }

        fun get(): Repository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}