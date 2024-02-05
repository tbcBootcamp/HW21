package com.example.hw21.presentation.mapper

import com.example.hw21.domain.model.ClothesDomainModel
import com.example.hw21.presentation.model.ClothesUiModel


fun ClothesDomainModel.toPresentation() = ClothesUiModel(
    id = id,
    cover = cover,
    price = price,
    title = title,
    favorite = favorite,
    category
)

fun ClothesUiModel.toDomain() = ClothesDomainModel(
    id = id,
    cover = cover,
    price = price,
    title = title,
    favorite = favorite,
    category
)