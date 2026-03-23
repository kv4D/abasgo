package com.example.abasgo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.abasgo.data.dao.FavouritePlaceDao
import com.example.abasgo.data.dao.ReviewDao
import com.example.abasgo.data.dao.UserDao
import com.example.abasgo.data.dao.VisitedPlaceDao
import com.example.abasgo.data.entity.User
import com.example.abasgo.data.entity.FavouritePlace

@Database(
    entities = [
        User::class,
        FavouritePlace::class,
   ],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favouritePlaceDao(): FavouritePlaceDao
    abstract fun visitedPlaceDao(): VisitedPlaceDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}