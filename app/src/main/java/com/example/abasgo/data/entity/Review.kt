package com.example.abasgo.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    "reviews",
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
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val placeId: Long,
    val userId: Long,

    val createdDate: Long,
    val rating: Int,
    val note: String
)

data class ReviewWithUser(
    @Embedded val review: Review,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val user: User
)
