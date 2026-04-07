package com.example.abasgo.ui.state

import com.example.abasgo.data.entity.FavouritePlace

sealed class FavouriteState {
    data object Loading : FavouriteState()
    data class Success(val places: List<FavouritePlace>) : FavouriteState()
    data class Error(val message: String) : FavouriteState()
}