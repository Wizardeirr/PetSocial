package com.example.petsocial.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [UserInfo::class], version = 1, exportSchema = true)
abstract class UserDatabase:RoomDatabase() {
    abstract fun userInfoDao():UserInfoDao
}