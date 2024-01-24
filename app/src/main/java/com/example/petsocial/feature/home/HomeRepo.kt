package com.example.petsocial.feature.home

import com.google.firebase.firestore.FirebaseFirestore

class HomeRepo : HomeRepoImpl {
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    var postList = ArrayList<HomeData>()
    override fun getPostsData() {
        database.collection("posts").document().addSnapshotListener { value, error ->
            try {
                if (error != null && value != null) {
                    val documents = value.data
                    if (documents != null) {
                        for (document in documents) {
                            documents["posts"]
                            val title = documents["postTitle"].toString()
                            val image = documents["postPhoto"].toString()
                            val homeData = HomeData(title, image)
                            postList.add(homeData)

                        }
                    }
                }
            } catch (_: Exception) {

            }

        }

    }

}