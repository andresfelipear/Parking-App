package com.vancouverparking.parkingapp2

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp

class App : Application()
{
    companion object {
        lateinit var APP_CONTEXT: Context
    }

    override fun onCreate() {
        super.onCreate()
        APP_CONTEXT = this

        //Initialize Firebase
        FirebaseApp.initializeApp(this)
    }
}