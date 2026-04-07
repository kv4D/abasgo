package com.example.abasgo.ui

import androidx.annotation.DrawableRes
import com.example.abasgo.R


data class AppIcon(
    @DrawableRes val iconRes: Int,
    val label: String
)

object AppIcons {
    val Map = AppIcon(R.drawable.ic_map, "Карта")
    val Favourite = AppIcon(R.drawable.ic_favorite, "Избранное")
    val History = AppIcon(R.drawable.ic_history, "История")
    val Cube = AppIcon(R.drawable.ic_cube, "Рулетка")
    val Menu = AppIcon(R.drawable.ic_menu, "Меню")
    val Settings = AppIcon(R.drawable.ic_settings, "Настройки")
    val Edit = AppIcon(R.drawable.ic_edit, "Изменить")
    val Heart = AppIcon(R.drawable.ic_heart, "Сердце")
    val Delete = AppIcon(R.drawable.ic_delete, "Удалить")
}

fun getIconRes(name: String): AppIcon {
    return when (name.uppercase()) {
        "MAP" -> AppIcons.Map
        "FAVOURITE" -> AppIcons.Favourite
        "HISTORY" -> AppIcons.History
        "ROULETTE" -> AppIcons.Cube
        "MENU" -> AppIcons.Menu
        "SETTINGS" -> AppIcons.Settings
        else -> AppIcons.Map
    }
}
