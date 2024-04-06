package com.example.petsocial.feature.postdetail

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.example.petsocial.util.Resource
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class PostDetailRepositoryImpl@Inject constructor(
    val glide: RequestManager
) : PostDetailRepository {

    override fun takePost(id: String, imageView: ImageView):Resource<Boolean> {
        return try {
            val storageRef = FirebaseStorage.getInstance().reference.child("posts").child(id)
            val result=storageRef.listAll()
                .addOnSuccessListener { listResult ->
                    listResult.items.firstOrNull()?.downloadUrl
                        ?.addOnSuccessListener { uri ->
                            // Glide kütüphanesi ile ImageView'e dosyayı yükleme
                            glide
                                .load(uri)
                                .into(imageView)
                        }
                }
             return Resource.success(result.isSuccessful)
        } catch (e: Exception) {
            Resource.error("An error occured:${e.message}", null)
        }
    }
}