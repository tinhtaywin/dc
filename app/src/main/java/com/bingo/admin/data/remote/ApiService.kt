package com.bingo.admin.data.remote

import com.bingo.admin.data.model.LoginResponse
import com.bingo.admin.data.model.PricesResponse
import com.bingo.admin.data.model.PriceUpdateRequest
import com.bingo.admin.data.model.SpecialUpdateRequest
import com.bingo.admin.data.model.UpdateResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    
    @POST("api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
    
    @GET("api/data")
    suspend fun getData(
        @Header("Authorization") token: String
    ): PricesResponse
    
    @PUT("api/update-item")
    suspend fun updateItem(
        @Header("Authorization") token: String,
        @Body priceUpdateRequest: PriceUpdateRequest
    ): UpdateResponse
    
    @PUT("api/update-special")
    suspend fun updateSpecial(
        @Header("Authorization") token: String,
        @Body specialUpdateRequest: SpecialUpdateRequest
    ): UpdateResponse
    
    // Request data classes
    data class LoginRequest(
        val username: String,
        val password: String
    )
}
