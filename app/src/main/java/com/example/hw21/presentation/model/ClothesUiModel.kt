package com.example.hw21.presentation.model


data class ClothesUiModel(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    var favorite: Boolean
)
