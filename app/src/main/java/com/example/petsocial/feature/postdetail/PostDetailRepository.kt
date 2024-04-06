package com.example.petsocial.feature.postdetail

import android.widget.ImageView
import com.example.petsocial.util.Resource

interface PostDetailRepository {
    fun takePost(id:String,imageView: ImageView):Resource<Boolean>
}