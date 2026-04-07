package com.example.abasgo.data.local

import com.example.abasgo.data.entity.User
import com.example.abasgo.data.entity.VisitedPlace
import com.example.abasgo.data.entity.FavouritePlace
import com.example.abasgo.data.local.dao.FavouritePlaceDao
import com.example.abasgo.data.local.dao.UserDao
import com.example.abasgo.data.local.dao.VisitedPlaceDao
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [User::class, VisitedPlace::class, FavouritePlace::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    exportSchema = true
)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favouritePlaceDao(): FavouritePlaceDao
    abstract fun visitedPlaceDao(): VisitedPlaceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration(dropAllTables = true).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
