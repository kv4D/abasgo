package com.example.abasgo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.abasgo.data.entity.User
import com.example.abasgo.data.entity.UserFavouritePlaces
import com.example.abasgo.data.entity.UserVisitedPlaces

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE id=:id")
    fun findById(id: Long): User

    @Insert
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Transaction
    @Query("SELECT * FROM users WHERE id=:id")
    fun getUserFavouritePlaces(id: Long): LiveData<List<UserFavouritePlaces>>

    @Transaction
    @Query("SELECT * FROM users WHERE id=:id")
    fun getUserVisitedPlaces(id: Long): LiveData<List<UserVisitedPlaces>>
}