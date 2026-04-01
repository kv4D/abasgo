package com.example.abasgo.ui.state

sealed class UserState {
    object Guest : UserState()
    data class Authorized(
        val username: String,
        val level: Int
    ) : UserState()
}