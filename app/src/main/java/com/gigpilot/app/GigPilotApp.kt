package com.gigpilot.app

import android.app.Application

class GigPilotApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // SQLCipher native library must be loaded before the encrypted DB opens.
        System.loadLibrary("sqlcipher")
    }
}
