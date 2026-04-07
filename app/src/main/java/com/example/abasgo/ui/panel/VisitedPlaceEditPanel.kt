package com.example.abasgo.ui.panel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.abasgo.ui.component.DefaultButton
import com.example.abasgo.ui.component.Panel
import com.example.abasgo.ui.component.Switch
import com.example.abasgo.ui.state.VisitedEditState
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.Black
import com.example.abasgo.ui.theme.BrightGreen
import com.example.abasgo.ui.theme.White
import com.example.abasgo.ui.viewmodel.VisitedEditViewModel

@Composable
fun VisitedPlaceEditPanel(
    onCancel: () -> Unit,
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: VisitedEditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Panel(modifier.fillMaxHeight()) {
        Text(
            text = "История путешественника",
            style = MaterialTheme.typography.titleMedium,
            color = BrightGreen
        )
        Text(
            text = "Редактирование",
            style = MaterialTheme.typography.titleSmall,
            color = BrightGreen
        )

        when (uiState) {
            is VisitedEditState.Loading -> CircularProgressIndicator()
            is VisitedEditState.Error -> {
                Text(
                    text = (uiState as VisitedEditState.Error).message,
                    color = Attention
                )
                DefaultButton(
                    text = "Назад",
                    onClick = onCancel,
                    colors = ButtonColors(
                        containerColor = BrightGreen,
                        contentColor = White,
                        disabledContainerColor = Attention,
                        disabledContentColor = Black
                    ),
                )
            }

            is VisitedEditState.Success -> {
                val place = (uiState as VisitedEditState.Success).place
                var note by rememberSaveable(place.id) { mutableStateOf(place.note.orEmpty()) }

                Text(
                    text = place.name ?: "Без названия",
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Заметка") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    DefaultButton(
                        text = "Сохранить",
                        onClick = {
                            viewModel.saveNote(note)
                            onSaved()
                        },
                        colors = ButtonColors(
                            containerColor = BrightGreen,
                            contentColor = White,
                            disabledContainerColor = Attention,
                            disabledContentColor = Black
                        ),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    DefaultButton(
                        text = "Отмена",
                        onClick = onCancel,
                        colors = ButtonColors(
                            containerColor = Attention,
                            contentColor = White,
                            disabledContainerColor = Attention,
                            disabledContentColor = Black
                        ),
                    )
                }
            }
        }
    }
}

