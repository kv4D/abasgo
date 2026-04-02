package com.example.abasgo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.abasgo.data.repository.VisitedPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VisitedViewModel @Inject constructor(
    private val repository: VisitedPlaceRepository
) : ViewModel() {
    
}