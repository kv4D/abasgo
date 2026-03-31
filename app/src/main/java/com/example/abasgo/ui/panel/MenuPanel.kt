package com.example.abasgo.ui.panel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.abasgo.ui.component.Avatar
import com.example.abasgo.ui.component.DefaultButton
import com.example.abasgo.ui.component.Panel
import com.example.abasgo.ui.component.Setting
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.Black
import com.example.abasgo.ui.theme.BrightGreen
import com.example.abasgo.ui.theme.White


@Composable
fun MenuPanel(modifier: Modifier = Modifier,) {
    Panel(modifier) {
        Text(
            text="Меню",
            style = MaterialTheme.typography.titleMedium,
            color = BrightGreen
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Avatar()
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text="Username",
                    style = MaterialTheme.typography.labelLarge,
                    color = White
                )
                Text(
                    text="Уровень 1337",
                    style = MaterialTheme.typography.labelMedium,
                    color = BrightGreen
                )
            }
        }

        Setting("Настройка 1")
        Setting("Настройка 2")
        Setting("Настройка 3")
        Setting("Настройка 4")
        Setting("Настройка 5")

        DefaultButton(
            onClick = {},
            colors = ButtonColors(
                containerColor = Attention,
                contentColor = Black,
                disabledContainerColor = Attention,
                disabledContentColor = Black
            ),
            text = "Выход"
        )

        Text(
            text="ABASgo. withered. 2026",
            style = MaterialTheme.typography.labelSmall,
            color = White
        )
    }
}