package com.example.abasgo.ui.panel

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.abasgo.ui.component.FavoritePlace
import com.example.abasgo.ui.component.Panel
import com.example.abasgo.ui.theme.BrightGreen

@Composable
fun FavoritePanel(modifier: Modifier = Modifier,) {
    Panel(modifier.fillMaxHeight()) {
        Text(
            text="Избранное путешественника",
            style = MaterialTheme.typography.titleMedium,
            color = BrightGreen
        )

        FavoritePlace("Крутое место")
    }
}