package com.example.abasgo.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.abasgo.data.entity.FavouritePlace
import com.example.abasgo.ui.AppIcons
import com.example.abasgo.ui.events.FavouriteEvent
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
        DefaultButton(
            colors = ButtonColors(
                containerColor = Attention,
                contentColor = Black,
                disabledContainerColor = Attention,
                disabledContentColor = Black
            ),
            icon = painterResource(AppIcons.Heart),
            text = "Удалить",
            onClick = { onEvent(FavouriteEvent.DeletePlaceClicked(place)) }
        )
    }
}