package com.example.petsocial.feature.registiration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsocial.room.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistirationViewModel @Inject constructor(private val signUpRepository: RegistirationRepositoryImpl) :
    ViewModel() {

    private val _registrationSuccess = MutableStateFlow<Boolean?>(null)
    val registrationSuccess: StateFlow<Boolean?> get() = _registrationSuccess

    private val _selectedDate = MutableStateFlow<String?>(null)
    val selectedDate: StateFlow<String?> get() = _selectedDate

    private val _loadingState = MutableStateFlow<Boolean?>(null)
    val loadingState: StateFlow<Boolean?> get() = _loadingState


    private val _errorString = MutableStateFlow<String?>(null)
    val errorString: StateFlow<String?> get() = _errorString

    fun createUser(userInfo: UserInfo, userPassword: String) {
        _loadingState.value = true
        viewModelScope.launch {
            val loginResult = signUpRepository.signUpCheck(userInfo, userPassword)
            _registrationSuccess.value = loginResult.data
            if (loginResult.data == null) {
                _errorString.value = loginResult.message
            }
            _loadingState.value = false
        }
    }
    suspend fun formatDate(year: Int, month: Int, day: Int) {
        val formattedDate = signUpRepository.dateRepository(year, month, day)
        _selectedDate.value = formattedDate
    }
}