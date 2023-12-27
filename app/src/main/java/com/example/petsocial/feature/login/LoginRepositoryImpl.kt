package com.example.petsocial.feature.login

import android.util.Log
import com.example.petsocial.room.UserInfo
import com.example.petsocial.room.UserInfoDao
import com.example.petsocial.util.Resource
import com.example.petsocial.util.Util
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor( private val userInfoDao: UserInfoDao) : LoginRepository {


    var database: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun loginUser(username: String, password: String): Resource<Boolean> {
        return try {
            val user = Util.auth.signInWithEmailAndPassword(username, password).await()
            if (user != null) {
                return Resource.success(true)
            } else {
                Resource.error("Error Occurred", false)
            }
        } catch (e: Exception) {
            Log.d("SignInRepo", "checkUser: ${e.message}")
            return Resource.error("${e.message}", null)
        }
    }

    override suspend fun getUserInformation(): Resource<UserInfo> {
        return try {
            val currentUser=Util.auth.currentUser
            if (currentUser != null) {
                val user =
                    database.collection("users").document(currentUser.uid).get().await()
                if (user != null) {
                    val userModel = UserInfo(
                        id = user["id"].toString(),
                        userEmail = user["userEmail"].toString(),
                        userImage = user["userImage"].toString(),
                        userName = user["userName"].toString(),
                        petName = user["petName"].toString(),
                        birthday = user["birthday"].toString()
                    )
                    userInfoDao.insertUsers(userModel)
                    return Resource.success(
                        userModel
                    )
                } else {
                    Resource.error("Error Occurred", null)
                }
            } else {
                Resource.error("Error Occurred", null)
            }
        } catch (e: Exception) {
            Log.d("SignInRepo", "checkUser: ${e.message}")
            return Resource.error("${e.message}", null)
        }
    }
}