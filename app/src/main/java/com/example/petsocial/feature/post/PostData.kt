package com.example.petsocial.feature.post

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostData(
    var id: String,
    val userId: String,
    val animalType: String,
    val animalGenius: String,
    val animalAge: String,
    val animalVacation: String,
    val animalEstrusPeriod: String,
    val postTitle: String,
):Parcelable

