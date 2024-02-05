package com.example.hw21.data.global.mapper

import com.example.hw21.data.global.model.ClothesDto
import com.example.hw21.domain.model.ClothesDomainModel

fun ClothesDto.toDomain() =
    ClothesDomainModel(id = id, cover = cover, price = price, title = title, favorite = favorite,category)