package com.example.petsocial.feature.home

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()


    private val _postList = MutableStateFlow<MutableList<HomeData>>(mutableListOf())
    val postList: StateFlow<MutableList<HomeData>> get() = _postList

    private val _loadingState = MutableStateFlow<Boolean?>(null)
    val loadingState: StateFlow<Boolean?> get() = _loadingState
    private val _errorString = MutableStateFlow<String?>(null)
    val errorString: StateFlow<String?> get() = _errorString
    private val _postSuccess = MutableStateFlow<Boolean?>(null)
    val postSuccess: StateFlow<Boolean?> get() = _postSuccess

    fun takePosts() {
        _loadingState.value = true

    }

    fun getPostsData() {
        database.collection("posts")
            .addSnapshotListener { value, error ->
                if (error != null) {
                } else
                    if (value != null) {
                        if (!value.isEmpty) {
                            val documents = value.documents

                            var list = mutableListOf<HomeData>()
                            for (document in documents) {
                                document.get("userProfileInfo")
                                val title = document.get("postTitlte").toString()
                                val image = document.get("postPhoto").toString()
                                val homeData = HomeData(title, image)
                                list.add(homeData)

                            }

                            _postList.value = list

                        }
                    }
            }
    }
}