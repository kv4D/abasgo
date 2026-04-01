package com.example.abasgo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.abasgo.data.entity.FavouritePlace
import com.example.abasgo.data.entity.User
import com.example.abasgo.data.entity.UserFavouritePlaces

@Dao
interface FavouritePlaceDao {
    @Query("SELECT * FROM favourite_places")
    fun getAll(): LiveData<List<FavouritePlace>>
    @Delete
    suspend fun delete(place: FavouritePlace)

    @Insert
    suspend fun insert(place: FavouritePlace)

    @Query("SELECT COUNT(*) FROM favourite_places")
    fun getAmount(): LiveData<Int>
}