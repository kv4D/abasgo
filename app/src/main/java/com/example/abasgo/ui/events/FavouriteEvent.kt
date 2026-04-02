package com.example.abasgo.ui.events

import com.example.abasgo.data.entity.FavouritePlace

// Events related to favourites
sealed class FavouriteEvent {
    // TODO: pass args when not demo
    data object AddPlaceClicked : FavouriteEvent()
}