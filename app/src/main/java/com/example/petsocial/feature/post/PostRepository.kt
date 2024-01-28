package com.example.petsocial.feature.post

import android.net.Uri
import com.example.petsocial.util.Resource

interface PostRepository {

    fun savePost(postData: PostData, files : List<Uri>): Resource<Boolean>


}