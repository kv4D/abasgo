package com.example.abasgo.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    "favourite_places",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class FavouritePlace(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val placeId: Long,
    val userId: Long,
    val longitude: Long,
    val latitude: Long
)
