package com.example.hw21.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clothes_table")
data class ClothesEntity(
    @PrimaryKey val id: Int = 0,
    val cover: String,
    val price: String,
    val title: String,
    val favourite: Boolean
)
