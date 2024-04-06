package com.example.petsocial.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.petsocial.feature.post.PostData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _postList = MutableStateFlow<MutableList<PostData>>(mutableListOf())
    val postList: StateFlow<MutableList<PostData>> get() = _postList

    private val _loadingState = MutableStateFlow<Boolean?>(null)
    val loadingState: StateFlow<Boolean?> get() = _loadingState
    private val _errorString = MutableStateFlow<String?>(null)
    val errorString: StateFlow<String?> get() = _errorString
    private val _postSuccess = MutableStateFlow<Boolean?>(null)
    val postSuccess: StateFlow<Boolean?> get() = _postSuccess



    fun getPostsData() {
        _loadingState.value = true
        database.collection("posts")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d("petsocialclub", "getPostsData: error posts")
                } else
                    if (value != null) {
                        if (!value.isEmpty) {
                            val documents = value.documents
                            val list = mutableListOf<PostData>()
                            for (document in documents) {
                                document.get("userProfileInfo")
                                val postId = document.get("id").toString()
                                val animalAge = document.get("animalAge").toString()
                                val animalEstrusPeriod =
                                    document.get("animalEstrusPeriod").toString()
                                val animalGenius = document.get("animalGenius").toString()
                                val animalType = document.get("animalType").toString()
                                val animalVacation = document.get("animalVacation").toString()
                                val postTitle = document.get("postTitle").toString()
                                val userId = document.get("userId").toString()

                                val postData = PostData(
                                    postId,
                                    userId,
                                    animalType,
                                    animalGenius,
                                    animalAge,
                                    animalVacation,
                                    animalEstrusPeriod,
                                    postTitle
                                )
                                list.add(postData)
                            }

                            _postList.value = list
                            _loadingState.value=false

                        }
                    }
            }
    }

}