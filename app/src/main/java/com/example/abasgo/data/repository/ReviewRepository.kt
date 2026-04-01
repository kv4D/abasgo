package com.example.abasgo.data.repository

import com.example.abasgo.data.local.dao.ReviewDao
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val reviewDao: ReviewDao
) {

}