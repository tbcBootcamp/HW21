package com.example.hw21.data.local.mapper

import com.example.hw21.data.local.model.ClothesEntity
import com.example.hw21.domain.model.ClothesDomainModel


fun ClothesEntity.toDomain() = ClothesDomainModel(id, cover, price, title, favourite)

fun ClothesDomainModel.toEntity() = ClothesEntity(id, cover, price, title, favorite)