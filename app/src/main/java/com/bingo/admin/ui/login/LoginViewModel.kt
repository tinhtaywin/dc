package com.bingo.admin.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bingo.admin.data.remote.ApiService
import com.bingo.admin.data.remote.ApiService.LoginRequest
import com.bingo.admin.data.remote.ApiService.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService
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
    
    fun login() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = ""
            
            try {
                val response = apiService.login(
                    LoginRequest(username.value, password.value)
                )
                
                // Store token (in real app, use DataStore or Secure Preferences)
                // For now, we'll just mark as logged in
                _isLoggedIn.value = true
                
            } catch (e: Exception) {
                _error.value = "Login failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _error.value = ""
    }
}