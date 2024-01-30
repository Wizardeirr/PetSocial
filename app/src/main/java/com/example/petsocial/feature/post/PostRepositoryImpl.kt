package com.example.petsocial.feature.post

import android.net.Uri
import android.util.Log
import com.example.petsocial.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.IOException
import java.util.UUID

class PostRepositoryImpl:PostRepository {
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    var storageRef = Firebase.storage.reference;
    override fun savePost(postData: PostData, files : List<Uri>): Resource<Boolean> {
        return try {
            run {

                database.collection("posts").document(postData.id).set(postData)
                files.forEach {
                    val id = UUID.randomUUID()
                    storageRef.child("posts").child(postData.id).child(id.toString()).putFile(it)
                }
                Resource.success(true)
            }
        } catch (e: IOException) {
            Log.d("PostRepository", "PostRepositoryCatch: ${e.message}")
            Resource.error("An error occured:${e.message}",null)
        }
    }


}