package com.example.hw21.presentation.mapper

import com.example.hw21.domain.model.ClothesDomainModel
import com.example.hw21.presentation.model.ClothesUiModel


fun ClothesDomainModel.toPresentation() = ClothesUiModel(id, cover, price, title, favorite)

fun ClothesUiModel.toDomain() = ClothesDomainModel(id, cover, price, title, favorite)