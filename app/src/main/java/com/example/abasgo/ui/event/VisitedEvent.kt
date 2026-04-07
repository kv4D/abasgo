package com.example.abasgo.ui.event

import com.example.abasgo.data.entity.VisitedPlace

// Events related to visited
sealed class VisitedEvent {
    // TODO: pass args when not demo
    data object AddPlaceClicked : VisitedEvent()
    data class PlaceSelected(val place: VisitedPlace) : VisitedEvent()
}