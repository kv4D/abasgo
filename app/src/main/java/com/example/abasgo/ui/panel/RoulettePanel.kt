package com.example.abasgo.ui.panel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.abasgo.ui.AppIcons
import com.example.abasgo.ui.component.DefaultButton
import com.example.abasgo.ui.component.Panel
import com.example.abasgo.ui.component.Switch
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.Black
import com.example.abasgo.ui.theme.BrightGreen
import com.example.abasgo.ui.theme.LightGreen
import com.example.abasgo.ui.theme.White


@Composable
fun RoulettePanel(modifier: Modifier = Modifier,) {
    Panel(modifier) {
        Text(
            text="Рулетка путешественника",
            style = MaterialTheme.typography.titleMedium,
            color = BrightGreen
        )
        Text(
            text="Пусть случайность создаст приключение",
            style = MaterialTheme.typography.titleSmall,
            color = White
        )
        Icon(
            painter = painterResource(AppIcons.Cube.iconRes),
            contentDescription = "",
            tint = Attention,
            modifier = Modifier.size(128.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text="Убрать посещенные места",
                style = MaterialTheme.typography.labelMedium,
                color = White
            )
            Spacer(modifier = Modifier.width(12.dp))
            Switch()
        }
        DefaultButton(
            text = "Вперед",
            onClick = {},
            colors = ButtonColors(
                containerColor = LightGreen,
                contentColor = White,
                disabledContainerColor = Attention,
                disabledContentColor = Black
            )
        )
    }
}