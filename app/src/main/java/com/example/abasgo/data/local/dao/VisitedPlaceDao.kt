package com.example.abasgo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.abasgo.data.entity.VisitedPlace
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitedPlaceDao {
    @Query("SELECT * FROM visited_places")
    fun getAll(): Flow<List<VisitedPlace>>
    @Delete
    suspend fun delete(place: VisitedPlace)

    @Insert
    suspend fun insert(place: VisitedPlace)

    @Query("SELECT COUNT(*) FROM visited_places")
    fun getAmount(): Flow<Int>

    @Query("UPDATE visited_places SET note = :note WHERE id = :id")
    suspend fun updateNote(note: String, id: Long)
}