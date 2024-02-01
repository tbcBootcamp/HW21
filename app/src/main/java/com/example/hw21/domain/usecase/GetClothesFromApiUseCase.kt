package com.example.hw21.domain.usecase

import com.example.hw21.data.global.common.Resource
import com.example.hw21.data.global.mapper.asResource
import com.example.hw21.domain.model.ClothesDomainModel
import com.example.hw21.domain.repository.global.GlobalClothesRepository
import com.example.hw21.presentation.mapper.toPresentation
import com.example.hw21.presentation.model.ClothesUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClothesFromApiUseCase @Inject constructor(private val remoteClothesRepository: GlobalClothesRepository) {
    suspend operator fun invoke(): Flow<Resource<List<ClothesDomainModel>>> {
        return remoteClothesRepository.getClothes()
    }
}
