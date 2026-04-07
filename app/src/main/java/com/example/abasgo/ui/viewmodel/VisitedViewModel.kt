package com.example.abasgo.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abasgo.data.repository.VisitedPlaceRepository
import com.example.abasgo.ui.AppRoute
import com.example.abasgo.ui.event.VisitedEvent
import com.example.abasgo.ui.state.VisitedUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisitedViewModel @Inject constructor(
    private val repository: VisitedPlaceRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<VisitedUIState>(VisitedUIState.Loading)
    val uiState: StateFlow<VisitedUIState> = _uiState.asStateFlow()

    init {
        loadPlaces()
    }

    fun loadPlaces() {
        viewModelScope.launch {
            repository.getAll()
                .collect { places ->
                    _uiState.value = VisitedUIState.Success(places)
                }
        }
    }

    fun addPlace() = viewModelScope.launch {
        // TODO: replace demo logic later
        val num = (1..100).random()
        repository.add(
            placeId = 0,
            name = "Крутое место $num",
            note = "Крутое описание крутого места, как я круто там сходил и видел крутые вещи!"
        )
    }

    fun onEvent(event: VisitedEvent) {
        when (event) {
            is VisitedEvent.AddPlaceClicked -> {
                addPlace()
            }

            is VisitedEvent.PlaceSelected -> {
            }
        }
    }
}