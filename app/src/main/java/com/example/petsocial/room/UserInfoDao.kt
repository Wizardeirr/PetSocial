package com.example.petsocial.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: UserInfo)

    @Query("SELECT * FROM users where  id like :userId")
    fun fetchLocalUser(userId : String): List<UserInfo>
    @Update
    fun updateLocalUser(userInfo: UserInfo)

}