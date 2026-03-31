package com.example.abasgo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.abasgo.data.entity.FavouritePlace
import com.example.abasgo.data.entity.ReviewWithUser
import com.example.abasgo.data.entity.UserFavouritePlaces

@Dao
interface ReviewDao {
    @Transaction
    @Query("""
        SELECT * FROM reviews 
        WHERE placeId = :placeId 
        """)
    suspend fun getPlaceReviewsWithUsers(placeId: Long): List<ReviewWithUser>

    @Delete
    suspend fun delete(place: FavouritePlace)

    @Insert
    suspend fun insert(place: FavouritePlace)
}