package com.example.hw21.domain.usecase

import com.example.hw21.domain.repository.local.LocalClothesRepository
import com.example.hw21.presentation.mapper.toDomain
import com.example.hw21.presentation.model.ClothesUiModel
import javax.inject.Inject

class AddUserToDataBaseUseCase @Inject constructor(private val localClothesRepository: LocalClothesRepository) {
    suspend operator fun invoke(clothes: List<ClothesUiModel>){
        localClothesRepository.addClothes(clothes.map { it.toDomain() })
    }
}