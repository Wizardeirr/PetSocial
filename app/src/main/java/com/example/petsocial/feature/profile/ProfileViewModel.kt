package com.example.petsocial.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsocial.room.UserInfo
import com.example.petsocial.util.Status
import com.example.petsocial.util.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor( private val profileRepository: ProfileRepositoryImpl) : ViewModel() {


    val userInfo:UserInfo?=null
    private val _user = MutableStateFlow<UserInfo?>(null)
    val user: MutableStateFlow<UserInfo?> get() = _user
    private val _userUpdate = MutableStateFlow<UserInfo?>(null)
    val userUpdate: MutableStateFlow<UserInfo?> get() = _userUpdate

    private val _loadingState = MutableStateFlow<Boolean?>(null)
    val loadingState: StateFlow<Boolean?> get() = _loadingState

    private val _errorString = MutableStateFlow<String?>(null)
    val errorString: StateFlow<String?> get() = _errorString


    init {
        fetchUserInformation()
        userInfo?.let { updateUserInformation(it) }
    }

    private fun fetchUserInformation(){
        _loadingState.value = true
        viewModelScope.launch {
            val response = profileRepository.fetchFromLocale(Util.auth.currentUser!!.uid)
            when(response.status){
                Status.SUCCESS -> {
                    _loadingState.value = false
                    response.data?.let { _user.emit(it) }
                }
                Status.ERROR -> {
                    _loadingState.value = false
                    _errorString.value = response.message
                }
                Status.LOADING -> {
                }
            }
        }
    }
    private fun updateUserInformation(userInfo: UserInfo){
        _loadingState.value = true
        viewModelScope.launch {
            val response=profileRepository.updateUserInformation(userInfo,Util.auth.currentUser!!.uid)
            when(response.status){
                Status.SUCCESS->{
                    _loadingState.value = false
                    response.data?.let {
                        _userUpdate.emit(it[0])
                    }
                }
                Status.ERROR->{
                    _loadingState.value = false
                    _errorString.value=response.message
                }
                Status.LOADING->{

                }
            }
        }

    }
}