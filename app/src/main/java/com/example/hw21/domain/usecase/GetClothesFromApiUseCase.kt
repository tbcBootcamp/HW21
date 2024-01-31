package com.example.hw21.domain.usecase

import com.example.hw21.data.global.common.Resource
import com.example.hw21.data.global.mapper.asResource
import com.example.hw21.domain.repository.global.GlobalClothesRepository
import com.example.hw21.presentation.mapper.toPresentation
import com.example.hw21.presentation.model.ClothesUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClothesFromApiUseCase @Inject constructor(private val remoteClothesRepository: GlobalClothesRepository) {
    suspend operator fun invoke(): Flow<Resource<List<ClothesUiModel>>> {
        return remoteClothesRepository.getClothes()
            .asResource { clothes -> clothes.map { it.toPresentation() } }
    }
}
