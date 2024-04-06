package com.example.petsocial.feature.privatemessage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.example.petsocial.common.Constants.VIEW_TYPE_RAW_RECEIVER
import com.example.petsocial.common.Constants.VIEW_TYPE_RAW_SENDER
import com.example.petsocial.databinding.HomePostRawBinding
import com.example.petsocial.databinding.ReceiverMessageRawBinding
import com.example.petsocial.databinding.SenderMessageRawBinding
import com.example.petsocial.util.Util.auth
import javax.inject.Inject

class PrivateMessageRecyclerAdapter @Inject constructor(
    val glide: RequestManager,

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ReceiverAdapterVH(binding: ReceiverMessageRawBinding) : RecyclerView.ViewHolder(binding.root)
    class SenderAdapterVH(binding: SenderMessageRawBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this@PrivateMessageRecyclerAdapter, diffUtil)
    private var message: List<Message>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        HomePostRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when (viewType) {
            VIEW_TYPE_RAW_SENDER -> {
                val binding = SenderMessageRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SenderAdapterVH(binding)
            }
            VIEW_TYPE_RAW_RECEIVER -> {
                val binding = ReceiverMessageRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ReceiverAdapterVH(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return message.size
    }

    override fun getItemViewType(position: Int): Int {
        val chat = message[position]
        return if (chat.fromUUID == auth.currentUser?.uid.toString()) {
            VIEW_TYPE_RAW_SENDER
        } else {
            VIEW_TYPE_RAW_RECEIVER
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = HomePostRawBinding.bind(holder.itemView)
        val imageView = binding.postRawImage

        val post = message[position]
      /*  val storageRef = FirebaseStorage.getInstance().reference.child("posts").child(post.id)
        storageRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.firstOrNull()?.downloadUrl
                    ?.addOnSuccessListener { uri ->
                        // Glide kütüphanesi ile ImageView'e dosyayı yükleme
                        glide
                            .load(uri)
                            .into(imageView)
                    }
            }*/
    }

}
