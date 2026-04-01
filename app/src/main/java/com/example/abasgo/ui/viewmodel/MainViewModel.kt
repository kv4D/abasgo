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

    fun getCurrentPanel(): AppPanel {
        return state.panelStack.last()
    }

    fun pushPanel(panel: AppPanel) {
        state = state.copy(
            panelStack = state.panelStack + panel
        )
    }

    fun popPanel() {
        if (state.panelStack.isNotEmpty()) {
            state = state.copy(
                panelStack = state.panelStack.dropLast(1)
            )
        }
    }

    fun replacePanel(newPanel: AppPanel) {
        if (state.panelStack.isNotEmpty()) {
            state = state.copy(
                panelStack = state.panelStack.dropLast(1)
            )
            state = state.copy(
                panelStack = state.panelStack + newPanel
            )
        }
    }
}