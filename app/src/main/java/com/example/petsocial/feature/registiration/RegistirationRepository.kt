package com.example.petsocial.feature.registiration

import com.example.petsocial.room.UserInfo
import com.example.petsocial.util.Resource

interface RegistirationRepository {
    suspend fun signUpCheck(userInfo: UserInfo, userPassword: String):Resource<Boolean>
    suspend fun dateRepository(year: Int, month: Int, day: Int):String
}