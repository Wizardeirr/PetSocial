package com.example.petsocial.feature.post

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsocial.common.Constants.CAT_GENIUS_OPTIONS
import com.example.petsocial.common.Constants.CAT_VACCINE_OPTIONS
import com.example.petsocial.common.Constants.DOG_GENIUS_OPTIONS
import com.example.petsocial.common.Constants.DOG_VACCINE_OPTIONS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepositoryImpl
) : ViewModel() {
    private val _loadingState = MutableStateFlow<Boolean?>(null)
    val loadingState: StateFlow<Boolean?> get() = _loadingState
    private val _errorString = MutableStateFlow<String?>(null)
    val errorString: StateFlow<String?> get() = _errorString
    private val _postSuccess = MutableStateFlow<Boolean?>(null)
    val postSuccess: StateFlow<Boolean?> get() = _postSuccess

    private val _dogVaccineOptions = MutableStateFlow<List<String>>(listOf())
    val dogVaccineOptions: StateFlow<List<String>> get() = _dogVaccineOptions
    private val _catVaccineOptions = MutableStateFlow<List<String>>(listOf())
    val catVaccineOptions: StateFlow<List<String>> get() = _catVaccineOptions
    private val _dogGeniusOptions = MutableStateFlow<List<String>>(listOf())
    val dogGeniusOptions: StateFlow<List<String>> get() = _dogGeniusOptions
    private val _catGeniusOptions = MutableStateFlow<List<String>>(listOf())
    val catGeniusOptions: StateFlow<List<String>> get() = _catGeniusOptions

    init {
        catGeniusOptions()
        vaccineCatSpinnerList()
        vaccineDogSpinnerList()
        dogGeniusList()
    }

    fun postSave(postData: PostData, files : List<Uri>) {
        _loadingState.value = true
        viewModelScope.launch {
            val createPost = postRepository.savePost(postData, files)
            _postSuccess.value = createPost.data
            if (createPost.data==null){
                _errorString.value=createPost.message
            }
            _loadingState.value = false
        }
    }

    private fun catGeniusOptions() {
        _catGeniusOptions.value = CAT_GENIUS_OPTIONS
    }

    private fun dogGeniusList() {
        _dogGeniusOptions.value = DOG_GENIUS_OPTIONS
    }

    private fun vaccineCatSpinnerList() {
        _catVaccineOptions.value = CAT_VACCINE_OPTIONS
    }

    private fun vaccineDogSpinnerList() {

        _dogVaccineOptions.value = DOG_VACCINE_OPTIONS
    }
}