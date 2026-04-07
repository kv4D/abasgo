package com.example.abasgo.ui.event

import com.example.abasgo.data.entity.FavouritePlace

// Events related to favourites
sealed class FavouriteEvent {
    // TODO: pass args when not demo
    data object AddPlaceClicked : FavouriteEvent()
    data class DeletePlaceClicked(val place: FavouritePlace) : FavouriteEvent()
}