package com.example.abasgo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abasgo.data.entity.FavouritePlace
import com.example.abasgo.data.repository.FavouritePlaceRepository
import com.example.abasgo.ui.events.FavouriteEvent
import com.example.abasgo.ui.state.FavouriteUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: FavouritePlaceRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<FavouriteUIState>(FavouriteUIState.Loading)
    val uiState: StateFlow<FavouriteUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll()
                .collect { places ->
                    _uiState.value = FavouriteUIState.Success(places)
                }
        }
    }

    fun addPlace() = viewModelScope.launch {
        // TODO: replace demo logic later
        val num = (1..100).random()
        repository.add("Место $num")
    }

    fun deletePlace(place: FavouritePlace) = viewModelScope.launch {
        repository.delete(place)
    }

    fun onEvent(event: FavouriteEvent) {
        when (event) {
            is FavouriteEvent.AddPlaceClicked -> {
                addPlace()
            }
            is FavouriteEvent.DeletePlaceClicked -> {
                deletePlace(event.place)
            }
        }
    }
}