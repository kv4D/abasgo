package com.example.abasgo.data.repository

import com.example.abasgo.data.local.dao.VisitedPlaceDao
import javax.inject.Inject

class VisitedPlaceRepository @Inject constructor(
    private val visitedPlaceDao: VisitedPlaceDao
) {

}