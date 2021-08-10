package com.testprojevosoft

import android.app.Application

class TestApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Repository.initialize()
    }

}