package com.example.abasgo.ui.state

import com.example.abasgo.data.entity.VisitedPlace

sealed class VisitedUIState {
    data object Loading : VisitedUIState()
    data class Success(val places: List<VisitedPlace>) : VisitedUIState()
    data class Error(val message: String) : VisitedUIState()
}