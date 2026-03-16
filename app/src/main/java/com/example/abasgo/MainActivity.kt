package com.example.abasgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.abasgo.ui.theme.ABASgoTheme
import com.example.abasgo.ui.AppIcons
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.Black
import com.example.abasgo.ui.theme.DarkGreen
import com.example.abasgo.ui.theme.LightGreen
import com.example.abasgo.ui.theme.White
import com.example.abasgo.ui.SystemBars
import com.example.abasgo.ui.getIconRes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ABASgoTheme {
                ABASgoApp()
            }
        }
    }
}

@Composable
fun NavigationBar(
    current: AppDestinations,
    onSelect: (AppDestinations) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(70.dp))
            .background(DarkGreen)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AppDestinations.entries.forEach { destination ->

            val isSelected = destination == current

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (isSelected) LightGreen else DarkGreen,
                        shape = CircleShape
                    )
                    .clickable { onSelect(destination) }
            ) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()

                ) {
                    Icon(
                        painter = painterResource(id = getIconRes(destination.name)),
                        contentDescription = destination.label,
                        tint = if (isSelected) Attention else White,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = destination.label,
                        textAlign = TextAlign.Center,
                        color = White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Поиск места") },
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = null,
                tint = White
            )
        },
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = DarkGreen,
            unfocusedContainerColor = DarkGreen,
            focusedTextColor = White,
            unfocusedTextColor = White,
            cursorColor = White,
            focusedPlaceholderColor = White,
            unfocusedPlaceholderColor = White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    )
}

@Composable
fun Panel(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(DarkGreen)
            .verticalScroll(scrollState)
            .padding(20.dp),
            content = content
    )
}

@Composable
fun FavoritePlace(placeName: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            placeName,
            style = MaterialTheme.typography.labelLarge,
            color = White
        )
        Button(
            onClick = {  },
            colors = ButtonColors(
                containerColor = Attention,
                contentColor = Black,
                disabledContainerColor = Attention,
                disabledContentColor = Black
            ),
            content = {
                Text("Удалить", style = MaterialTheme.typography.bodyLarge)
                Icon(
                    painter = painterResource(AppIcons.Heart),
                    contentDescription = "Лайк",
                    tint = Black
                )
            }
        )
    }
}

@Composable
fun Favorite() {
    Text(
        text="Избранное путешественника",
        style = MaterialTheme.typography.titleMedium,
        color = LightGreen
    )

    FavoritePlace("Крутое место")
}

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
                tint = White
            )
        }
    }
}

@Composable
fun History() {
    Text(
        text="История путешественника",
        style = MaterialTheme.typography.titleMedium,
        color = White
        )
    HistoryEntry("Крутое место 2", "01.12.2022")
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

@Composable
fun Roulette() {
    Text(
        text="Рулетка путешественника",
        style = MaterialTheme.typography.titleMedium,
        color = White
    )
    Text(
        text="Пусть случайность создаст приключение",
        style = MaterialTheme.typography.titleSmall,
        color = White
    )
    Icon(
        painter = painterResource(AppIcons.Cube),
        contentDescription = "",
        tint = Attention,
        modifier = Modifier.size(128.dp)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text="Убрать посещенные места",
            style = MaterialTheme.typography.labelMedium,
            color = White
        )
        Spacer(modifier = Modifier.width(12.dp))
        Switch()
    }
    Button(
        onClick = {},
        colors = ButtonColors(
            containerColor = LightGreen,
            contentColor = White,
            disabledContainerColor = Attention,
            disabledContentColor = Black
        ),
        content = { Text("Вперед!", style = MaterialTheme.typography.bodyLarge) }
    )
}

@Composable
fun Switch() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = White,
            checkedTrackColor = Attention,
            uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
            uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
        )
    )
}

@Composable
fun Setting(name: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clip(RoundedCornerShape(28.dp))
            .background(LightGreen)
            .padding(all = 20.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(AppIcons.Settings),
                contentDescription = name,
                tint = White
            )
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

@Composable
fun Avatar() {
    Image(
        painter = painterResource(id = R.drawable.default_avatar),
        contentDescription = "Аватар",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .size(75.dp)
    )
}

@Composable
fun Menu() {
    Text(
        text="Меню",
        style = MaterialTheme.typography.titleMedium,
        color = White
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {
        Avatar()
        Column {
            Text(
                text="Username",
                style = MaterialTheme.typography.labelLarge,
                color = White
            )
            Text(
                text="Уровень 1337",
                style = MaterialTheme.typography.labelMedium,
                color = LightGreen
            )
        }
    }
    Button(
        onClick = {},
        colors = ButtonColors(
            containerColor = Attention,
            contentColor = Black,
            disabledContainerColor = Attention,
            disabledContentColor = Black
        ),
        content = { Text("Выход") }
    )

    Setting("Настройка 1")
    Setting("Настройка 2")
    Setting("Настройка 3")
    Setting("Настройка 4")
    Setting("Настройка 5")

    Text(
        text="ABASgo. withered. 2026",
        style = MaterialTheme.typography.labelSmall,
        color = White
    )
}

@PreviewScreenSizes
@Composable
fun ABASgoApp() {

    SystemBars(
        statusBarColor = DarkGreen,
        navigationBarColor = DarkGreen,
        darkIcons = false
    )

    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.MAP) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                current = currentDestination,
                onSelect =  {currentDestination = it }
            )
        },
        topBar = {
            SearchBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 12.dp),
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentDestination != AppDestinations.MAP){
                Panel(
                    content = {
                        when (currentDestination) {
                            AppDestinations.FAVORITE -> Favorite()
                            AppDestinations.HISTORY -> History()
                            AppDestinations.ROULETTE -> Roulette()
                            AppDestinations.MENU -> Menu()
                            else -> {}
                        } },
                )
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
) {
    MAP("Карта"),
    FAVORITE("Избранное"),
    HISTORY("История"),
    ROULETTE("Рулетка"),
    MENU("Меню"),
}