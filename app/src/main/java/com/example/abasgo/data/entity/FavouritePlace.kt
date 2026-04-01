package com.example.abasgo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favourite_places",)
data class FavouritePlace(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val osmId: Long?,
    val longitude: Long?,
    val latitude: Long?,

    val name: String? = null
)
