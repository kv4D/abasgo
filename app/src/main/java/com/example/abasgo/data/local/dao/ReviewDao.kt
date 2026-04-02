package com.example.abasgo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.abasgo.data.entity.Review
import com.example.abasgo.data.entity.ReviewWithUser
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Transaction
    @Query("""
        SELECT * FROM reviews 
        WHERE placeId = :placeId 
        """)
    fun getPlaceReviewsWithUsers(placeId: Long): Flow<List<ReviewWithUser>>

    @Delete
    suspend fun delete(review: Review)

    @Insert
    suspend fun insert(review: Review)

    @Query("SELECT COUNT(*) FROM reviews")
    fun getAmount(): Flow<Int>
}