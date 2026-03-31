package com.example.abasgo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.abasgo.ui.AppIcons
import com.example.abasgo.ui.theme.LightGreen
import com.example.abasgo.ui.theme.White


@Composable
fun Setting(name: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clip(RoundedCornerShape(28.dp))
            .background(LightGreen)
            .padding(all = 12.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(AppIcons.Settings),
                contentDescription = name,
                tint = White
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text=name,
                    style = MaterialTheme.typography.labelMedium,
                    color = White
                )
                Text(
                    text="Какое-то описание",
                    style = MaterialTheme.typography.labelSmall,
                    color = White
                )
            }
        }
        Switch()
    }
}