package com.example.abasgo.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.example.abasgo.ui.theme.Black

@Composable
fun DefaultButton(
    text: String,
    colors: ButtonColors,
    onClick: () -> Unit,
    icon: Painter? = null
) {
    Button(
        onClick = onClick,
        colors = colors,
        content = {
            Text(text = text, style = MaterialTheme.typography.labelMedium)

            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = text,
                    tint = Black
                )
            }
        }
    )
}