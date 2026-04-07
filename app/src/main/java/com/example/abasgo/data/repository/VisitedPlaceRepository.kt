package com.example.abasgo.data.repository

import com.example.abasgo.data.entity.VisitedPlace
import com.example.abasgo.data.local.dao.VisitedPlaceDao
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class VisitedPlaceRepository @Inject constructor(
    private val visitedPlaceDao: VisitedPlaceDao
) {
    fun getAll() : Flow<List<VisitedPlace>> {
        return visitedPlaceDao.getAll()
    }

    fun getById(id: Long): Flow<VisitedPlace?> {
        return visitedPlaceDao.getById(id)
    }

    suspend fun add(placeId: Long, name: String? = null, note: String? = null) {
        val currentDate = LocalDate.now()
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

    fun getAmount(): Flow<Int> {
        return visitedPlaceDao.getAmount()
    }

    suspend fun updateNote(place: VisitedPlace, note: String) {
        visitedPlaceDao.updateNote(note, place.id)
    }

    suspend fun updateNoteById(placeId: Long, note: String) {
        visitedPlaceDao.updateNote(note, placeId)
    }
}