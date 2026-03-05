package com.example.abasgo

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource


object AppIcons {
    @DrawableRes
    val Map = R.drawable.ic_map

    @DrawableRes
    val Favorite = R.drawable.ic_favorite

    @DrawableRes
    val History = R.drawable.ic_history

    @DrawableRes
    val Cube = R.drawable.ic_cube

    @DrawableRes
    val Menu = R.drawable.ic_menu

    @DrawableRes
    val Settings = R.drawable.ic_settings

    @DrawableRes
    val Edit = R.drawable.ic_edit

    @DrawableRes
    val Heart = R.drawable.ic_heart
}

fun getIconRes(name: String): Int {
    return when (name) {
        "MAP" -> AppIcons.Map
        "FAVORITE" -> AppIcons.Favorite
        "HISTORY" -> AppIcons.History
        "ROULETTE" -> AppIcons.Cube
        "MENU" -> AppIcons.Menu
        "SETTINGS" -> AppIcons.Settings
        else -> AppIcons.Map
    }
}
