package com.example.abasgo.data.repository

import com.example.abasgo.data.entity.FavouritePlace
import com.example.abasgo.data.local.dao.FavouritePlaceDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouritePlaceRepository @Inject constructor(
    private val favouritePlaceDao: FavouritePlaceDao
) {
    fun getAll() : Flow<List<FavouritePlace>> {
        return favouritePlaceDao.getAll()
    }

    suspend fun add(name: String? = null) {
        val place = FavouritePlace(
            osmId = 0,
            longitude = 0,
            latitude = 0,
            name = name)
        favouritePlaceDao.insert(place)
    }

    suspend fun delete(place: FavouritePlace) {
        favouritePlaceDao.delete(place)
    }

    fun getAmount(): Flow<Int> {
        return favouritePlaceDao.getAmount()
    }
}