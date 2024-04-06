package com.example.petsocial.feature.postdetail

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsocial.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postDetailRepositoryImpl: PostDetailRepositoryImpl

):ViewModel() {
    private val _takePostData = MutableStateFlow<Boolean?>(null)
    val takePostData: StateFlow<Boolean?> get() = _takePostData

    private val _loadingState = MutableStateFlow<Boolean?>(null)
    val loadingState: StateFlow<Boolean?> get() = _loadingState

    private val _errorString = MutableStateFlow<String?>(null)
    val errorString: StateFlow<String?> get() = _errorString


    fun showPostDetails(id: String?, imageView: ImageView){
        _loadingState.value=true
        viewModelScope.launch {
            val takePostResponse=postDetailRepositoryImpl.takePost(id.toString(),imageView)
            when(takePostResponse.status){
                Status.SUCCESS->{
                    takePostResponse.data?.let { _takePostData.emit(it) }
                }
                Status.LOADING->{
                    _loadingState.value=true
                }
                Status.ERROR->{
                    _loadingState.value=false
                    _errorString.value=takePostResponse.message
                }
            }
        }
    }
}