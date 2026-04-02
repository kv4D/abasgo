package com.example.abasgo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.abasgo.data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id=:id")
    fun findById(id: Long): User

    @Insert
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)
}