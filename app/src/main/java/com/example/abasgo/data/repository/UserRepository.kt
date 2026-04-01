package com.example.abasgo.data.repository

import com.example.abasgo.data.local.dao.UserDao
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

}