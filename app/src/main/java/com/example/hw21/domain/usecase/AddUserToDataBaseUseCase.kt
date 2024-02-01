package com.example.hw21.domain.usecase

import com.example.hw21.domain.model.ClothesDomainModel
import com.example.hw21.domain.repository.local.LocalClothesRepository
import com.example.hw21.presentation.mapper.toDomain
import com.example.hw21.presentation.model.ClothesUiModel
import javax.inject.Inject

class AddUserToDataBaseUseCase @Inject constructor(private val localClothesRepository: LocalClothesRepository) {
    suspend operator fun invoke(clothes: List<ClothesDomainModel>){
        localClothesRepository.addClothes(clothes)
    }
}