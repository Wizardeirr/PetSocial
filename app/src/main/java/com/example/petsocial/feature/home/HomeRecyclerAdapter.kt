package com.example.petsocial.feature.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.petsocial.databinding.HomePostRawBinding
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
            glide.load(post.postImage).into(imageView)
            postTitle.text = post.title
        }

    }
}