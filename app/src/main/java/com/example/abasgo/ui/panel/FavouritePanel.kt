package com.example.abasgo.ui.panel

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.abasgo.ui.component.DefaultButton
import com.example.abasgo.ui.component.FavouritePlace
import com.example.abasgo.ui.component.Panel
import com.example.abasgo.ui.event.FavouriteEvent
import com.example.abasgo.ui.state.FavouriteState
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.Black
import com.example.abasgo.ui.theme.BrightGreen
import com.example.abasgo.ui.theme.White

@Composable
fun FavouritePanel(
    uiState: FavouriteState,
    onEvent: (FavouriteEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Panel(modifier.fillMaxHeight()) {
        Text(
            text="Избранное путешественника",
            style = MaterialTheme.typography.titleMedium,
            color = BrightGreen
        )
        when (uiState) {
            is FavouriteState.Loading -> {
                CircularProgressIndicator()
            }
            is FavouriteState.Success -> {
                val places = uiState.places
                Text(
                    text="Количество: ${places.size}",
                    style = MaterialTheme.typography.titleSmall,
                    color = BrightGreen
                )
                LazyColumn(
                    modifier = Modifier.weight(1f),
                ) {
                    items(
                        items = places,
                        key = { it.id }
                    ) { place ->
                            FavouritePlace(place, onEvent = onEvent)
                    }
                }
            }
            is FavouriteState.Error -> {
                Text(
                    text = uiState.message,
                    color = Attention,
                )
            }
        }
        DefaultButton(
            onClick = { onEvent(FavouriteEvent.AddPlaceClicked) },
            colors = ButtonColors(
                containerColor = BrightGreen,
                contentColor = White,
                disabledContainerColor = Attention,
                disabledContentColor = Black
            ),
            text = "Добавить место (ДЕМО, КАРТА БУДЕТ ПОЗЖЕ)"
        )
    }
}