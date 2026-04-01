package com.example.abasgo.data.repository

import androidx.lifecycle.LiveData
import com.example.abasgo.data.entity.FavouritePlace
import com.example.abasgo.data.local.dao.FavouritePlaceDao
import javax.inject.Inject

class FavouritePlaceRepository @Inject constructor(
    private val favouritePlaceDao: FavouritePlaceDao
) {
    fun getAll() : LiveData<List<FavouritePlace>> {
        return favouritePlaceDao.getAll()
    }

    suspend fun add(placeId: Long, name: String? = null) {
        val place = FavouritePlace(
            osmId = placeId,
            longitude = 0,
            latitude = 0,
            name = name)
        favouritePlaceDao.insert(place)
    }

    suspend fun delete(place: FavouritePlace) {
        favouritePlaceDao.delete(place)
    }

    fun getAmount(): LiveData<Int> {
        return favouritePlaceDao.getAmount()
    }
}