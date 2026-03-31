package com.example.abasgo.ui.state


enum class AppPanel(
    val label: String,
) {
    DEFAULT("Стандарт"),
    FAVORITE("Избранное"),
    HISTORY("История"),
    ROULETTE("Рулетка"),
    MENU("Меню"),
}

data class MainState(
    val activePanel: AppPanel = AppPanel.DEFAULT
)