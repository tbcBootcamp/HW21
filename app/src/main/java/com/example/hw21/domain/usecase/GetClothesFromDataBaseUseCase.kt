package com.example.hw21.domain.usecase

import com.example.hw21.domain.repository.local.LocalClothesRepository
import com.example.hw21.presentation.mapper.toPresentation
import com.example.hw21.presentation.model.ClothesUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetClothesFromDataBaseUseCase @Inject constructor(private val localClothesRepository: LocalClothesRepository) {
    operator fun invoke(): Flow<List<ClothesUiModel>> {
        return localClothesRepository.getClothes().map { it.map { it.toPresentation() } }
    }
}
