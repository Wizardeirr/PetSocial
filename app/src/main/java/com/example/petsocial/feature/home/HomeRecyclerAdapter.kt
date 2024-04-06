package com.example.petsocial.feature.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.petsocial.R
import com.example.petsocial.databinding.HomePostRawBinding
import com.example.petsocial.feature.post.PostData
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class HomeRecyclerAdapter @Inject constructor(
    val glide: RequestManager,
) : RecyclerView.Adapter<HomeRecyclerAdapter.HomeAdapterVH>() {
    class HomeAdapterVH(binding: HomePostRawBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<PostData>() {
        override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this@HomeRecyclerAdapter, diffUtil)
    var posts: List<PostData>
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

        val post = posts[position]
        holder.itemView.setOnClickListener { view ->
            val bundle = bundleOf(
                "postID" to post.id,
                "postTitle" to post.postTitle,
                "animalType" to post.animalType,
                "animalAge" to post.animalAge,
                "animalGenius" to post.animalGenius,
                "animalVacation" to post.animalVacation,
                "animalEstrusPeriod" to post.animalEstrusPeriod,
                "userID" to post.userId
                )
            view.findNavController()
                .navigate(R.id.action_homeFragment_to_postDetailFragment, bundle)
        }
        val storageRef = FirebaseStorage.getInstance().reference.child("posts").child(post.id)
        storageRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.firstOrNull()?.downloadUrl
                    ?.addOnSuccessListener { uri ->
                        // Glide kütüphanesi ile ImageView'e dosyayı yükleme
                        glide
                            .load(uri)
                            .into(imageView)
                    }
            }
        binding.postTitle.text = post.postTitle

    }
}
