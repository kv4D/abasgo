package com.example.abasgo.ui.panel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.abasgo.ui.component.DefaultButton
import com.example.abasgo.ui.component.Panel
import com.example.abasgo.ui.state.VisitedDetailState
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.Black
import com.example.abasgo.ui.theme.BrightGreen
import com.example.abasgo.ui.theme.White
import com.example.abasgo.ui.viewmodel.VisitedDetailViewModel

@Composable
fun VisitedPlaceDetailPanel(
    onEditClick: (placeId: Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: VisitedDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Panel(modifier.fillMaxHeight()) {
        Text(
            text = "История путешественника",
            style = MaterialTheme.typography.titleMedium,
            color = BrightGreen
        )
        Text(
            text = "Детали места",
            style = MaterialTheme.typography.titleSmall,
            color = BrightGreen
        )

        when (uiState) {
            is VisitedDetailState.Loading -> CircularProgressIndicator()
            is VisitedDetailState.Error -> {
                Text(
                    text = (uiState as VisitedDetailState.Error).message,
                    color = Attention
                )
            }

            is VisitedDetailState.Success -> {
                val place = (uiState as VisitedDetailState.Success).place

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = place.name ?: "Крутое место",
                        style = MaterialTheme.typography.titleMedium,
                        color = White,
                        )
                    Text(
                        text = "${place.visitDate}",
                        style = MaterialTheme.typography.titleSmall,
                        color = White,
                        )
                }

                Text(
                    text = place.note.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = White,
                )

                DefaultButton(
                    text = "Редактировать",
                    onClick = { onEditClick(place.id) },
                    colors = ButtonColors(
                        containerColor = BrightGreen,
                        contentColor = White,
                        disabledContainerColor = Attention,
                        disabledContentColor = Black
                    ),
                )
            }
        }
    }
}

