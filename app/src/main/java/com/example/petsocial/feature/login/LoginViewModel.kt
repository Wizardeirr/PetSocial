package com.example.petsocial.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsocial.room.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepositoryImpl): ViewModel() {
    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> get() = _loginSuccess

    private val _user = MutableSharedFlow<UserInfo?>()
    val userInfo: MutableSharedFlow<UserInfo?> get() = _user

    private val _loadingState = MutableStateFlow<Boolean?>(null)
    val loadingState: StateFlow<Boolean?> get() = _loadingState

    private val _errorString = MutableStateFlow<String?>(null)
    val errorString: StateFlow<String?> get() = _errorString.asStateFlow()

    fun loginUser(username: String, password : String){
        _loadingState.value=true
        viewModelScope.launch {
            val userCheck=loginRepository.loginUser(username, password)
            _loginSuccess.value=userCheck.data
            if (userCheck.data==null){
                _errorString.value=userCheck.message
            }
            _loadingState.value=false
        }
    }

    fun getUserInfo(){
        viewModelScope.launch {
            val data = loginRepository.getUserInformation()
            _user.emit(data.data)
            if (data.data == null){
                _errorString.value = data.message
            }
        }
    }
}