package com.bingo.admin.data.remote

import com.bingo.admin.data.model.GameItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    
    @POST("api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
    
    @GET("api/games")
    suspend fun getGameItems(
        @Header("Authorization") token: String
    ): List<GameItem>
    
    @PUT("api/games/{id}/price")
    suspend fun updateGamePrice(
        @Path("id") gameId: String,
        @Header("Authorization") token: String,
        @Body priceUpdateRequest: PriceUpdateRequest
    ): GameItem
    
    data class LoginRequest(
        val username: String,
        val password: String
    )
    
    data class LoginResponse(
        val token: String,
        val expiresIn: Long
    )
    
    data class PriceUpdateRequest(
        val newPrice: String
    )
}