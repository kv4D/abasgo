package com.example.abasgo.ui.state

import com.example.abasgo.data.entity.FavouritePlace

sealed class FavouriteUIState {
    data object Loading : FavouriteUIState()
    data class Success(val places: List<FavouritePlace>) : FavouriteUIState()
    data class Error(val message: String) : FavouriteUIState()
}