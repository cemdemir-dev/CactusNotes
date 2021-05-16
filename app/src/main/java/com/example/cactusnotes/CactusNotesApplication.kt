package com.example.cactusnotes

import android.app.Application
import com.example.cactusnotes.api.generateApi

class CactusNotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        generateApi(this)
    }
}