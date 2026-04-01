package com.example.abasgo.data.local

import android.content.Context
import androidx.room.Room
import com.example.abasgo.data.local.dao.FavouritePlaceDao
import com.example.abasgo.data.local.dao.UserDao
import com.example.abasgo.data.local.dao.VisitedPlaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "abasgo_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideFavouritePlaceDao(database: AppDatabase): FavouritePlaceDao {
        return database.favouritePlaceDao()
    }

    @Provides
    @Singleton
    fun provideVisitedPlaceDao(database: AppDatabase): VisitedPlaceDao {
        return database.visitedPlaceDao()
    }
}