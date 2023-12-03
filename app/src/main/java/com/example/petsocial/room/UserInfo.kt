package com.example.petsocial.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserInfo(
    @PrimaryKey
    var id: String,
    var userEmail: String,
    var userName: String,
    var petName: String,
    var userImage: String

)