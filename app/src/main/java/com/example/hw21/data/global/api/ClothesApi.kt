package com.example.hw21.data.global.api

import com.example.hw21.data.global.model.ClothesDto
import retrofit2.Response
import retrofit2.http.GET

interface ClothesApi {
    @GET("/v3/df8d4951-2757-45aa-8f60-bf1592a090ce")
    suspend fun getClothes(): Response<List<ClothesDto>>
}