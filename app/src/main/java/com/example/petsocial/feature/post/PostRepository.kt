package com.example.petsocial.feature.post

import com.example.petsocial.util.Resource

interface PostRepository {

    fun savePost(postData: PostData): Resource<Boolean>


}