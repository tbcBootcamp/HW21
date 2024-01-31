package com.example.hw21.data.repository.global

import com.example.hw21.data.global.api.ClothesApi
import com.example.hw21.data.global.common.HandleResponse
import com.example.hw21.data.global.common.Resource
import com.example.hw21.data.global.mapper.asResource
import com.example.hw21.data.global.mapper.toDomain
import com.example.hw21.domain.model.ClothesDomainModel
import com.example.hw21.domain.repository.global.GlobalClothesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GlobalClothesRepositoryImpl @Inject constructor(
    private val clothesApiService: ClothesApi,
    private val handleResponse: HandleResponse
) : GlobalClothesRepository {

    override suspend fun getClothes(): Flow<Resource<List<ClothesDomainModel>>> =
        handleResponse.safeApiCall {
            clothesApiService.getClothes()
        }.asResource { clothes ->
            clothes.map { it.toDomain() }
        }
}