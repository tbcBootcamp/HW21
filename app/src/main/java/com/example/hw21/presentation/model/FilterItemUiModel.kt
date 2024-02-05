package com.example.hw21.presentation.model

import com.example.hw21.presentation.screen.purchase.Category

data class FilterItemUiModel (
//    val filterName: String,
    val filterName: Category,
    var bgColor: Int,
    var txtColor: Int,
    var isActive: Boolean = false
)