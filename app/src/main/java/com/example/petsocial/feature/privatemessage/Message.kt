package com.example.petsocial.feature.privatemessage

data class Message(
    val uid:String,
    val fromUUID:String,
    val message:String,
    val timestamp: Long,
)
