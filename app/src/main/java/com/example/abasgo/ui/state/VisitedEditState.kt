package com.example.abasgo.ui.state

import com.example.abasgo.data.entity.VisitedPlace

sealed class VisitedEditState {
    data object Loading : VisitedEditState()
    data class Success(val place: VisitedPlace) : VisitedEditState()
    data class Error(val message: String) : VisitedEditState()
}
