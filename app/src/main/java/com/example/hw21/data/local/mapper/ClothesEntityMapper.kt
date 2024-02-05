package com.example.hw21.data.local.mapper

import com.example.hw21.data.local.model.ClothesEntity
import com.example.hw21.domain.model.ClothesDomainModel


fun ClothesEntity.toDomain() = ClothesDomainModel(id = id, cover = cover, price = price, title = title, favorite = favourite, category)

fun ClothesDomainModel.toEntity() = ClothesEntity(id = id, cover = cover, price = price, title = title, favourite = favorite, category)