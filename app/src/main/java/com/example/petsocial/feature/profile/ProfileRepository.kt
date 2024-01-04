package com.example.petsocial.feature.profile

import com.example.petsocial.room.UserInfo
import com.example.petsocial.util.Resource


interface ProfileRepository {


    fun fetchFromLocale(id : String) : Resource<UserInfo>

    suspend fun updateUserInformation(userInfo: UserInfo,id:String) : Resource<List<UserInfo>>


}