package com.example.hw21.di

import android.content.Context
import androidx.room.Room
import com.example.hw21.data.local.dao.ClothesDao
import com.example.hw21.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "clothes_database").build()
    }

    @Provides
    @Singleton
    fun provideClothesDao(appDatabase: AppDatabase): ClothesDao {
        return appDatabase.clothesDao()
    }
}