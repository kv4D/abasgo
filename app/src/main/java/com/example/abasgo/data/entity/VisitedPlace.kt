package com.example.abasgo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity("visited_places")
data class VisitedPlace(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val osmId: Long?,
    val longitude: Long?,
    val latitude: Long?,

    val name: String? = null,

    val visitDate: LocalDate?,
    val note: String?,
)
