package com.example.hw21.data.global.mapper

import com.example.hw21.data.global.model.ClothesDto
import com.example.hw21.domain.model.ClothesDomainModel

fun ClothesDto.toDomain() = ClothesDomainModel(id, cover, price, title, favorite)