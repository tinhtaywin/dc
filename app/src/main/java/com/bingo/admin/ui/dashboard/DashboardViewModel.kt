package com.bingo.admin.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bingo.admin.data.model.GameItem
import com.bingo.admin.data.model.PricesResponse
import com.bingo.admin.data.model.SpecialItem
import com.bingo.admin.data.repository.BingoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val mlbbItems: List<GameItem> = emptyList(),
    val pubgItems: List<GameItem> = emptyList(),
    val specialItem: SpecialItem? = null,
    val selectedTab: Int = 0, // 0 = MLBB, 1 = PUBG
    val updateSuccess: Boolean = false
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: BingoRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    // Store token from login
    fun setToken(token: String) {
        repository.setAuthToken(token)
    }
    
    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = "")
            
            repository.getGameData().collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            mlbbItems = data.mlbb,
                            pubgItems = data.pubg,
                            specialItem = data.special,
                            error = ""
                        )
                    },
                    onFailure = { e ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Failed to load data: ${e.message}"
                        )
                    }
                )
            }
        }
    }
    
    fun updateItem(id: String, category: String, newKs: String?, newTag: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = "")
            
            repository.updateItem(id, category, newKs, newTag).collect { result ->
                result.fold(
                    onSuccess = {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            updateSuccess = true,
                            error = ""
                        )
                        // Reload data to reflect changes
                        loadData()
                    },
                    onFailure = { e ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Failed to update: ${e.message}"
                        )
                    }
                )
            }
        }
    }
    
    fun updateSpecialItem(newKs: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = "")
            
            repository.updateSpecialPrice(newKs).collect { result ->
                result.fold(
                    onSuccess = {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            updateSuccess = true,
                            error = ""
                        )
                        loadData()
                    },
                    onFailure = { e ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Failed to update: ${e.message}"
                        )
                    }
                )
            }
        }
    }
    
    fun setSelectedTab(tabIndex: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = tabIndex)
    }
    
    fun clearUpdateSuccess() {
        _uiState.value = _uiState.value.copy(updateSuccess = false)
    }
    
    fun logout() {
        repository.logout()
    }
}
