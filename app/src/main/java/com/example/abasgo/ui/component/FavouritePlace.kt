package com.example.abasgo.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.abasgo.data.entity.FavouritePlace
import com.example.abasgo.ui.AppIcons
import com.example.abasgo.ui.event.FavouriteEvent
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.Black
import com.example.abasgo.ui.theme.White


@Composable
fun FavouritePlace(place: FavouritePlace, onEvent: (FavouriteEvent) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            place.name ?: "Крутое место",
            style = MaterialTheme.typography.labelLarge,
            color = White
        )
        IconButton(onClick = { onEvent(FavouriteEvent.DeletePlaceClicked(place)) }) {
            Icon(
                painter = painterResource(AppIcons.Delete.iconRes),
                contentDescription = "Удалить",
                tint = Attention,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}