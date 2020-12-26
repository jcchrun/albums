package com.jcchrun.albums

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlbumsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        setupStetho()
    }

    private fun setupStetho() {
        Stetho.initializeWithDefaults(this)
    }
}