package com.example.petsocial

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PetSocialApplication:Application() {
    companion object{
        lateinit var instance:PetSocialApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance=this

    }
}