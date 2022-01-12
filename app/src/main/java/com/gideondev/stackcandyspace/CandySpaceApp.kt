package com.gideondev.stackcandyspace

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CandySpaceApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
