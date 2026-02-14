package com.bingo.admin.data.repository

import com.bingo.admin.data.model.LoginResponse
import com.bingo.admin.data.model.PricesResponse
import com.bingo.admin.data.model.PriceUpdateRequest
import com.bingo.admin.data.model.SpecialUpdateRequest
import com.bingo.admin.data.model.UpdateResponse
import com.bingo.admin.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for handling all data operations.
 * Acts as a single source of truth for the app.
 */
@Singleton
class BingoRepository @Inject constructor(
    private val apiService: ApiService
) {
    // Store the auth token
    private var authToken: String = ""

    fun setAuthToken(token: String) {
        authToken = token
    }

    fun getAuthToken(): String = authToken

    /**
     * Login with username and password
     */
    fun login(username: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.login(
                ApiService.LoginRequest(username, password)
            )
            // Store the token
            setAuthToken(response.token)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    /**
     * Fetch all game data (prices, tags)
     */
    fun getGameData(): Flow<Result<PricesResponse>> = flow {
        try {
            val response = apiService.getData("Bearer $authToken")
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    /**
     * Update item price and/or tag
     */
    fun updateItem(
        id: String,
        category: String,
        newKs: String?,
        newTag: String?
    ): Flow<Result<UpdateResponse>> = flow {
        try {
            val request = PriceUpdateRequest(
                id = id,
                category = category,
                new_ks = newKs,
                new_tag = newTag
            )
            val response = apiService.updateItem("Bearer $authToken", request)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    /**
     * Update special item price
     */
    fun updateSpecialPrice(newKs: String): Flow<Result<UpdateResponse>> = flow {
        try {
            val request = SpecialUpdateRequest(newKs)
            val response = apiService.updateSpecial("Bearer $authToken", request)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    /**
     * Clear stored token (logout)
     */
    fun logout() {
        authToken = ""
    }
}
