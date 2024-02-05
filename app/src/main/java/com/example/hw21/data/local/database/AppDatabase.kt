package com.example.hw21.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.hw21.data.local.dao.ClothesDao
import com.example.hw21.data.local.model.ClothesEntity

@Database(
    entities = [ClothesEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clothesDao(): ClothesDao
}

val migration1to2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER table clothes_table ADD COLUMN category TEXT NOT NULL DEFAULT ''")
    }
}
