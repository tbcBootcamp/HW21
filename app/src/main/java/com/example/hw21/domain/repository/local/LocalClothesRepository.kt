package com.example.hw21.domain.repository.local

import com.example.hw21.domain.model.ClothesDomainModel
import kotlinx.coroutines.flow.Flow

interface LocalClothesRepository {
     fun getClothes(): Flow<List<ClothesDomainModel>>
    suspend fun addClothes(clothes: List<ClothesDomainModel>)
}