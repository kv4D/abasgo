package com.example.abasgo.ui.state


enum class AppPanel(
    val label: String,
) {
    MAP("Карта"),
    FAVORITE("Избранное"),
    HISTORY("История"),
    ROULETTE("Рулетка"),
    MENU("Меню"),
}

data class MainState(
    val currentPanel: AppPanel = AppPanel.MAP
)