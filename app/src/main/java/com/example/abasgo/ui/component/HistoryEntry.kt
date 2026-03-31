package com.example.abasgo.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.abasgo.ui.AppIcons
import com.example.abasgo.ui.theme.White

@Composable
fun HistoryEntry(placeName: String, date: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                placeName,
                style = MaterialTheme.typography.labelMedium,
                color = White
            )
            Text(
                "Посещено: $date",
                style = MaterialTheme.typography.bodySmall,
                color = White
            )
        }
        IconButton(onClick = {  }) {
            Icon(
                painter = painterResource(AppIcons.Edit),
                contentDescription = "Изменить запись",
                tint = White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}