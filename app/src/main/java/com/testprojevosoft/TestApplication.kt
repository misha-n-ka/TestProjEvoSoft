package com.testprojevosoft

import android.app.Application

class TestApplication: Application() {

    lateinit var settingsManager: SettingsManager

    override fun onCreate() {
        super.onCreate()
        instance = this
        Repository.initialize()
        settingsManager = SettingsManager(this)
    }

    companion object {

        private var instance: TestApplication? = null

        fun getApplicationInstance(): TestApplication {
            return instance ?: throw IllegalStateException("Application must be initialized")
        }
    }

}