package com.example.abasgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abasgo.ui.theme.ABASgoTheme
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.DarkGreen
import com.example.abasgo.ui.theme.LightGreen
import com.example.abasgo.ui.theme.White

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
            .padding(bottom = 20.dp)
            .padding(12.dp)
            .clip(RoundedCornerShape(70.dp))
            .background(DarkGreen)
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AppDestinations.entries.forEach { destination ->

            val isSelected = destination == current

            Box(
                modifier = Modifier
                    .size(75.dp)
                    .background(
                        color = if (isSelected) LightGreen else DarkGreen,
                        shape = CircleShape
                    )
                    .clickable { onSelect(destination) }
            ) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize().fillMaxHeight(),

                ) {
                    Icon(
                        painter = painterResource(id = getIconRes(destination.name)),
                        contentDescription = destination.label,
                        tint = if (isSelected) Attention else White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = destination.label,
                        textAlign = TextAlign.Center,
                        color = White,
                        fontSize = 12.sp
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
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(DarkGreen)
            .padding(20.dp)
    ) {
        Column(
            content = content
        )
    }
}

@Composable
fun Favorite() {
    Text("Избранное путешественника")
}

@Composable
fun History() {
    Text("История путешественника")
}

@Composable
fun Roulette() {
    Text("Рулетка путешественника")
}

@Composable
fun Menu() {
    Text("Меню")
}

@PreviewScreenSizes
@Composable
fun ABASgoApp() {
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
                    .padding(vertical = 20.dp),
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .padding(vertical = 20.dp),
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