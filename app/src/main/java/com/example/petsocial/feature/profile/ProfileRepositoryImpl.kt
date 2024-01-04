package com.example.petsocial.feature.profile

import com.example.petsocial.room.UserInfo
import com.example.petsocial.room.UserInfoDao
import com.example.petsocial.util.Resource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val userInfoDao: UserInfoDao) :ProfileRepository {
    override fun fetchFromLocale(id : String): Resource<UserInfo> {
        val response = userInfoDao.fetchLocalUser(id)
        return if (response.isEmpty()){
            Resource.success(null )
        } else {
            Resource.success(response
                    [0])
        }
    }

    override suspend fun updateUserInformation(userInfo: UserInfo,id:String): Resource<List<UserInfo>> {
        userInfoDao.updateLocalUser(userInfo)
        return run {
            val updatedUserInfo = userInfoDao.fetchLocalUser(id)
            Resource.success(updatedUserInfo)
        }
    }
}