package com.example.petsocial.feature.privatemessage

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class PrivateMessageViewModel :ViewModel(){
    private val _sendMessage = MutableStateFlow<MutableList<Message>>(mutableListOf())
    val sendMessage: StateFlow<MutableList<Message>> get() = _sendMessage


    fun sendPrivateMessage(senderUid: String, receiverUid: String, message: String) {
        val database = FirebaseDatabase.getInstance()
        val messagesRef = database.getReference("messages")

        val messageKey = messagesRef.push().key // Yeni bir mesaj için benzersiz bir anahtar oluştur
        val senderMessageRef = messagesRef.child(senderUid).child(receiverUid).child(messageKey!!)
        val receiverMessageRef = messagesRef.child(receiverUid).child(senderUid).child(messageKey)
        val timestamp = System.currentTimeMillis()
        val senderMessage = HashMap<String, Any>()

        senderMessage["senderUid"] = senderUid
        senderMessage["receiverUid"] = receiverUid
        senderMessage["message"] = message
        senderMessage["timestamp"] = timestamp
        val list = mutableListOf<Message>()
        val receiverMessage = HashMap<String, Any>()
        receiverMessage["senderUid"] = senderUid
        receiverMessage["receiverUid"] = receiverUid
        receiverMessage["message"] = message
        receiverMessage["timestamp"] = timestamp
        val sendMessage=Message(senderUid,receiverUid,message,timestamp)
        list.add(sendMessage)
        senderMessageRef.setValue(senderMessage)
        receiverMessageRef.setValue(receiverMessage)
        _sendMessage.value=list
    }
}