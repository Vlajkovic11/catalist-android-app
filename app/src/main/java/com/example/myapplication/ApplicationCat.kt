package com.example.myapplication

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationCat : Application(){
    override fun onCreate() {
        super.onCreate()
        Log.d("Application run", "Started successfully")
    }
}