package com.example.petsocial.feature.registiration

import android.util.Log
import com.example.petsocial.room.UserInfo
import com.example.petsocial.util.Resource
import com.example.petsocial.util.Util
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegistirationRepositoryImpl: RegistirationRepository {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var database:FirebaseFirestore=FirebaseFirestore.getInstance()
    override suspend fun signUpCheck(userInfo: UserInfo, userPassword: String):Resource<Boolean> {
        return try {
            val result = Util.auth.createUserWithEmailAndPassword(userInfo.userEmail, userPassword).await()
            if (result.user != null){
                userInfo.id = result.user!!.uid
                database.collection("users").document(userInfo.id).set(userInfo).await()
                Resource.success(true)
            }else{
                Resource.error("An error occured",null)
            }
        } catch (e: Exception) {
            Log.d("signUpRepository", "signUpCheck: ${e.message}")
            Resource.error("An error occured:${e.message}",null)
        }
    }


    override suspend fun dateRepository(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return dateFormat.format(calendar.time)
    }
}
