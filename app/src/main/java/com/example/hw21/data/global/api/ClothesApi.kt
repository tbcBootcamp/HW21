package com.example.hw21.data.global.api

import com.example.hw21.data.global.model.ClothesDto
import retrofit2.Response
import retrofit2.http.GET

interface ClothesApi {
    @GET("/v3/1775d634-92dc-4c32-ae71-1707b8cfee41")
    suspend fun getClothes(): Response<List<ClothesDto>>
}