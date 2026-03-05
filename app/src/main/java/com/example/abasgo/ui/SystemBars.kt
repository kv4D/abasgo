package com.example.abasgo.ui

import android.app.Activity
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SystemBars(
    statusBarColor: Color,
    navigationBarColor: Color,
    darkIcons: Boolean = false
) {

    val view = LocalView.current
    val isPreview = LocalInspectionMode.current

    if (!isPreview) {
        SideEffect {
            val window = (view.context as Activity).window

            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = navigationBarColor.toArgb()

            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = darkIcons
            controller.isAppearanceLightNavigationBars = darkIcons
        }
    }
}