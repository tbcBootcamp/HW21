package com.example.hw21.data.repository.local

import com.example.hw21.data.local.dao.ClothesDao
import com.example.hw21.domain.model.ClothesDomainModel
import com.example.hw21.data.local.mapper.toDomain
import com.example.hw21.data.local.mapper.toEntity
import com.example.hw21.domain.repository.local.LocalClothesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalClothesRepositoryImpl @Inject constructor(private val clothesDao: ClothesDao) :
    LocalClothesRepository {
    override  fun getClothes(): Flow<List<ClothesDomainModel>>  {
         return clothesDao.getAll().map { clothes -> clothes.map { it.toDomain() } }
    }

    override suspend fun addClothes(clothes: List<ClothesDomainModel>) = withContext(Dispatchers.IO){
        clothesDao.insertClothes(clothes.map { it.toEntity() })
    }

}