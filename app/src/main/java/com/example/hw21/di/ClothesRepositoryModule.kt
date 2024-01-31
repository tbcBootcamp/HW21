package com.example.hw21.di

import com.example.hw21.data.global.api.ClothesApi
import com.example.hw21.data.global.common.HandleResponse
import com.example.hw21.data.local.dao.ClothesDao
import com.example.hw21.data.repository.global.GlobalClothesRepositoryImpl
import com.example.hw21.data.repository.local.LocalClothesRepositoryImpl
import com.example.hw21.domain.repository.global.GlobalClothesRepository
import com.example.hw21.domain.repository.local.LocalClothesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClothesRepositoryModule {

    @Singleton
    @Provides
    fun provideRemoteClothesRepository(
        clothesApiService: ClothesApi,
        handleResponse: HandleResponse
    ): GlobalClothesRepository =
        GlobalClothesRepositoryImpl(
            clothesApiService = clothesApiService,
            handleResponse = handleResponse,
        )

    @Singleton
    @Provides
    fun provideLocalClothesRepository(clothesDao: ClothesDao): LocalClothesRepository =
        LocalClothesRepositoryImpl(clothesDao = clothesDao)
}