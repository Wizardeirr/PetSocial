package com.example.petsocial.feature.post

import android.util.Log
import com.example.petsocial.util.Resource
import com.example.petsocial.util.Util.auth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.IOException
import java.util.UUID

class PostRepositoryImpl:PostRepository {
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun savePost(postData: PostData): Resource<Boolean> {
        return try {
            val result = auth.currentUser?.uid
            run {
                if (result != null) {
                    database.collection("posts/${result}/${UUID.randomUUID()}").document().set(postData)
                }
                Resource.success(true)
            }
        } catch (e: IOException) {
            Log.d("PostRepository", "PostRepositoryCatch: ${e.message}")
            Resource.error("An error occured:${e.message}",null)
        }
    }


}