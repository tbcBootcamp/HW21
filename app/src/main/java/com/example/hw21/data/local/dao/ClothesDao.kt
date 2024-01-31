package com.example.hw21.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hw21.data.local.model.ClothesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothesDao {
    @Query("SELECT * FROM clothes_table")
    fun getAll(): Flow<List<ClothesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClothes(clothes: List<ClothesEntity>)
}