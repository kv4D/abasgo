package com.example.abasgo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.abasgo.ui.state.MainState
import com.example.abasgo.ui.state.AppPanel


class MainViewModel : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    fun openPanel(panel: AppPanel) {
        state = state.copy(activePanel = panel)
    }
}