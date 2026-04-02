package com.example.abasgo.ui.panel

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.abasgo.ui.component.DefaultButton
import com.example.abasgo.ui.component.HistoryEntry
import com.example.abasgo.ui.component.Panel
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.Black
import com.example.abasgo.ui.theme.BrightGreen
import com.example.abasgo.ui.theme.LightGreen
import com.example.abasgo.ui.theme.White


@Composable
fun HistoryPanel(modifier: Modifier = Modifier,) {
    Panel(modifier.fillMaxHeight()) {
        Text(
            text="История путешественника",
            style = MaterialTheme.typography.titleMedium,
            color = BrightGreen
        )
        HistoryEntry("Крутое место 2", "01.12.2022")
        DefaultButton(
            onClick = {},
            colors = ButtonColors(
                containerColor = BrightGreen,
                contentColor = White,
                disabledContainerColor = Attention,
                disabledContentColor = Black
            ),
            text = "Добавить место"
        )
        HorizontalDivider(
            color = LightGreen,
            thickness = 2.dp
        )
        Text(
            text="Дальше - больше!",
            style = MaterialTheme.typography.bodySmall,
            color = White
        )
    }
}