package com.example.petsocial

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PetSocialApplication:Application() {
    override fun onCreate() {
        super.onCreate()

    }
}