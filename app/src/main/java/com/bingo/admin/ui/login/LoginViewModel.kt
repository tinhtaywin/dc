package com.bingo.admin.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bingo.admin.data.repository.BingoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: BingoRepository
) : ViewModel() {
    
    var username = mutableStateOf("")
        private set
    
    var password = mutableStateOf("")
        private set
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()
    
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    
    // Store token for dashboard
    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token.asStateFlow()
    
    fun login() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = ""
            
            if (username.value.isBlank() || password.value.isBlank()) {
                _error.value = "Please enter username and password"
                _isLoading.value = false
                return@launch
            }
            
            repository.login(username.value, password.value).collect { result ->
                result.fold(
                    onSuccess = { response ->
                        _token.value = response.token
                        _isLoggedIn.value = true
                    },
                    onFailure = { e ->
                        _error.value = "Login failed: ${e.message}"
                    }
                )
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _error.value = ""
    }
}
