package com.example.petsocial.feature.login

import com.example.petsocial.room.UserInfo
import com.example.petsocial.util.Resource

interface LoginRepository {
     suspend fun loginUser(username: String, password : String): Resource<Boolean>

     suspend fun getUserInformation() : Resource<UserInfo>
}