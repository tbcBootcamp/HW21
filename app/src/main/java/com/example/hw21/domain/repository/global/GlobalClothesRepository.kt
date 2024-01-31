package com.example.hw21.domain.repository.global

import com.example.hw21.data.global.common.Resource
import com.example.hw21.domain.model.ClothesDomainModel
import kotlinx.coroutines.flow.Flow

interface GlobalClothesRepository {
    suspend fun getClothes(): Flow<Resource<List<ClothesDomainModel>>>
}