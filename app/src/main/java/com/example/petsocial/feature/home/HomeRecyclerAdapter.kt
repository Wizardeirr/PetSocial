package com.example.petsocial.feature.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.petsocial.databinding.HomePostRawBinding
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class
HomeRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<HomeRecyclerAdapter.HomeAdapterVH>() {
    class HomeAdapterVH(binding: HomePostRawBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<HomeData>() {
        override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this@HomeRecyclerAdapter, diffUtil)
    var posts: List<HomeData>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterVH {
        val view = HomePostRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeAdapterVH(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: HomeAdapterVH, position: Int) {
        val binding = HomePostRawBinding.bind(holder.itemView)
        val imageView = binding.postRawImage
        val postTitle = binding.postTitle
        val post = posts[position]

        binding.apply {
            val storageReference = FirebaseStorage.getInstance().reference.child("posts").child(post.postImage)
            storageReference.downloadUrl
                .addOnSuccessListener { uri ->
                    // URI başarıyla alındı, bu URI'yi kullanabilirsiniz
                    val downloadUrl = uri.toString()

                    // Şimdi bu URL'yi istediğiniz şekilde kullanabilirsiniz
                    // Örneğin, Glide ile ImageView'e resmi yükleyebilirsiniz
                    glide
                        .load(downloadUrl)
                        .into(imageView)
                }
                .addOnFailureListener { exception ->
                    // URI alınırken bir hata oluştu
                    Log.d("Storage", "Error getting download URL", exception)
                }
                glide.load(storageReference).into(imageView)

            postTitle.text = post.title
        }

    }
}