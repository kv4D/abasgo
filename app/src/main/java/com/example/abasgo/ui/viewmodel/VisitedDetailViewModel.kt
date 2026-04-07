package com.example.abasgo.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abasgo.data.entity.VisitedPlace
import com.example.abasgo.data.repository.VisitedPlaceRepository
import com.example.abasgo.ui.AppRoute
import com.example.abasgo.ui.state.VisitedDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisitedDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: VisitedPlaceRepository,
) : ViewModel() {
    private val placeId: Long = checkNotNull(savedStateHandle[AppRoute.HistoryDetail.ARG_PLACE_ID])

    private val _uiState = MutableStateFlow<VisitedDetailState>(VisitedDetailState.Loading)
    val uiState: StateFlow<VisitedDetailState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getById(placeId).collect { place ->
                _uiState.value = if (place != null) {
                    VisitedDetailState.Success(place)
                } else {
                    VisitedDetailState.Error("Место не найдено (id=$placeId)")
                }
            }
        }
    }
}

