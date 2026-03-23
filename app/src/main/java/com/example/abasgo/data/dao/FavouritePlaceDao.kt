package com.example.abasgo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.abasgo.data.entity.FavouritePlace
import com.example.abasgo.data.entity.UserFavouritePlaces

@Dao
interface FavouritePlaceDao {
    @Delete
    suspend fun delete(place: FavouritePlace)

    @Insert
    suspend fun insert(place: FavouritePlace)
}