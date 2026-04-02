package com.example.abasgo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.abasgo.data.entity.FavouritePlace
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritePlaceDao {
    @Query("SELECT * FROM favourite_places")
    fun getAll(): Flow<List<FavouritePlace>>
    @Delete
    suspend fun delete(place: FavouritePlace)

    @Insert
    suspend fun insert(place: FavouritePlace)

    @Query("SELECT COUNT(*) FROM favourite_places")
    fun getAmount(): Flow<Int>
}