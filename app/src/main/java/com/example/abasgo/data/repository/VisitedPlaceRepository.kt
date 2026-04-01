package com.example.abasgo.data.repository

import androidx.lifecycle.LiveData
import com.example.abasgo.data.entity.VisitedPlace
import com.example.abasgo.data.local.dao.VisitedPlaceDao
import javax.inject.Inject
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class VisitedPlaceRepository @Inject constructor(
    private val visitedPlaceDao: VisitedPlaceDao
) {
    fun getAll() : LiveData<List<VisitedPlace>> {
        return visitedPlaceDao.getAll()
    }

    suspend fun add(placeId: Long, name: String? = null, note: String? = null) {
        val currentDate = Date()
        val place = VisitedPlace(
            osmId = placeId,
            longitude = 0,
            latitude = 0,
            name = name,
            visitDate = currentDate,
            note = note)
        visitedPlaceDao.insert(place)
    }

    suspend fun delete(place: VisitedPlace) {
        visitedPlaceDao.delete(place)
    }

    fun getAmount(): LiveData<Int> {
        return visitedPlaceDao.getAmount()
    }

    suspend fun updateNote(place: VisitedPlace, note: String) {
        visitedPlaceDao
    }
}