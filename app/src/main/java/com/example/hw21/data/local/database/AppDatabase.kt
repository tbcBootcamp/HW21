package com.example.hw21.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hw21.data.local.dao.ClothesDao
import com.example.hw21.data.local.model.ClothesEntity

@Database(entities = [ClothesEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clothesDao(): ClothesDao
}