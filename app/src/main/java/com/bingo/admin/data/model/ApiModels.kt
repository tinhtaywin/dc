package com.bingo.admin.data.model

/**
 * API Response Models for the Bingo Admin App
 */

// Response from login endpoint
data class LoginResponse(
    val token: String,
    val expiresIn: Long
)

// Response from get prices endpoint
data class PricesResponse(
    val special: SpecialItem?,
    val mlbb: List<GameItem>,
    val pubg: List<GameItem>
)

// Special item model
data class SpecialItem(
    val name: String,
    val icon: String,
    val ks: String,
    val tag: String
)

// Update response
data class UpdateResponse(
    val message: String
)

// Request models
data class PriceUpdateRequest(
    val id: String,
    val category: String,
    val new_ks: String?,
    val new_tag: String?
)

data class SpecialUpdateRequest(
    val new_ks: String
)
