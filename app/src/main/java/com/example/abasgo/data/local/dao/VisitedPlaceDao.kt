package com.example.abasgo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.abasgo.data.entity.VisitedPlace

@Dao
interface VisitedPlaceDao {
    @Query("SELECT * FROM visited_places")
    fun getAll(): LiveData<List<VisitedPlace>>
    @Delete
    suspend fun delete(place: VisitedPlace)

    @Insert
    suspend fun insert(place: VisitedPlace)

    @Update
    suspend fun update(place: VisitedPlace): Int

    @Query("SELECT COUNT(*) FROM visited_places")
    fun getAmount(): LiveData<Int>
}