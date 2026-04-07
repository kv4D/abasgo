package com.example.abasgo.ui.state

import com.example.abasgo.data.entity.VisitedPlace


sealed class VisitedDetailState {
    data object Loading : VisitedDetailState()
    data class Success(val place: VisitedPlace) : VisitedDetailState()
    data class Error(val message: String) : VisitedDetailState()
}