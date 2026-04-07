package com.example.abasgo.ui.panel

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.abasgo.ui.component.DefaultButton
import com.example.abasgo.ui.component.Panel
import com.example.abasgo.ui.component.VisitedPlace
import com.example.abasgo.ui.event.VisitedEvent
import com.example.abasgo.ui.state.VisitedUIState
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.Black
import com.example.abasgo.ui.theme.BrightGreen
import com.example.abasgo.ui.theme.LightGreen
import com.example.abasgo.ui.theme.White

@Composable
fun HistoryPanel(
    uiState: VisitedUIState,
    onEvent: (VisitedEvent) -> Unit,
    onSelectPlace: (placeId: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Panel(modifier.fillMaxHeight()) {
        Text(
            text="История путешественника",
            style = MaterialTheme.typography.titleMedium,
            color = BrightGreen
        )
        when (uiState) {
            is VisitedUIState.Loading -> {
                CircularProgressIndicator()
            }
            is VisitedUIState.Success -> {
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
                        Spacer(Modifier.height(12.dp))
                        VisitedPlace(
                            place = place,
                            onEditClick = { onSelectPlace(place.id) }
                        )
                    }
                }
                DefaultButton(
                    onClick = { onEvent(VisitedEvent.AddPlaceClicked) },
                    colors = ButtonColors(
                        containerColor = BrightGreen,
                        contentColor = White,
                        disabledContainerColor = Attention,
                        disabledContentColor = Black
                    ),
                    text = "Добавить место (ДЕМО, КАРТА БУДЕТ ПОЗЖЕ)"
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
            is VisitedUIState.Error -> {
                Text(
                    text = uiState.message,
                    color = Attention,
                )
            }
        }
    }
}