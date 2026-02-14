package com.bingo.admin.data.model

data class GameItem(
    val id: String,
    val dia: String,
    val uc: String,  // For PUBG items, will be empty for MLBB
    val ks: String,
    val tag: String
)
