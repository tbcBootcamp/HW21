package com.example.hw21.domain.usecase

import com.example.hw21.domain.model.ClothesDomainModel
import com.example.hw21.domain.repository.local.LocalClothesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClothesFromDataBaseUseCase @Inject constructor(private val localClothesRepository: LocalClothesRepository) {
      operator fun invoke(): Flow<List<ClothesDomainModel>> {
        return localClothesRepository.getClothes()
    }
}
