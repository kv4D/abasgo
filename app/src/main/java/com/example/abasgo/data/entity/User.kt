package com.example.abasgo.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity("users", indices = [Index(value = ["username"])])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val username: String
)

data class UserFavouritePlaces(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val favouritePlaces: List<FavouritePlace>,
)

data class UserVisitedPlaces(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val favouritePlaces: List<FavouritePlace>,
)
