package com.example.abasgo.data.repository

import com.example.abasgo.data.local.dao.FavouritePlaceDao
import javax.inject.Inject

class FavouritePlaceRepository @Inject constructor(
    private val favouritePlaceDao: FavouritePlaceDao
) {

}