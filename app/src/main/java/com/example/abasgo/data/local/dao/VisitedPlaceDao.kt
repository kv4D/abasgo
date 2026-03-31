package com.example.abasgo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.abasgo.data.entity.VisitedPlace
import com.example.abasgo.data.entity.UserVisitedPlaces

@Dao
interface VisitedPlaceDao {
    @Delete
    suspend fun delete(place: VisitedPlace)

    @Insert
    suspend fun insert(place: VisitedPlace)
}