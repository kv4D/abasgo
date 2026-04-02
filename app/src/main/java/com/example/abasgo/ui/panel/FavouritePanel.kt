package com.example.abasgo.ui.panel

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.abasgo.ui.component.DefaultButton
import com.example.abasgo.ui.component.FavouritePlace
import com.example.abasgo.ui.component.Panel
import com.example.abasgo.ui.events.FavouriteEvent
import com.example.abasgo.ui.state.FavouriteUIState
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.Black
import com.example.abasgo.ui.theme.BrightGreen
import com.example.abasgo.ui.theme.White
import com.example.abasgo.ui.viewmodel.FavouriteViewModel

@Composable
fun FavouritePanel(
    uiState: FavouriteUIState,
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
            is FavouriteUIState.Loading -> {
                // wait till it loads
                CircularProgressIndicator()
            }
            is FavouriteUIState.Success -> {
                val places = uiState.places

                LazyColumn(
                    modifier = Modifier.weight(1f),
                ) {
                    items(
                        items = places,
                        key = { it.id }
                    ) { place ->
                        if (place.name == null) {
                            FavouritePlace(placeName = "Крутое место")
                        } else {
                            FavouritePlace(placeName = place.name)
                        }
                    }
                }
            }
            is FavouriteUIState.Error -> {
                Text(
                    text = (uiState as FavouriteUIState.Error).message,
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