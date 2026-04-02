package com.example.abasgo

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abasgo.ui.theme.ABASgoTheme
import com.example.abasgo.ui.theme.DarkGreen
import com.example.abasgo.ui.SystemBars
import com.example.abasgo.ui.component.NavigationBar
import com.example.abasgo.ui.component.SearchBar
import com.example.abasgo.ui.panel.FavouritePanel
import com.example.abasgo.ui.panel.HistoryPanel
import com.example.abasgo.ui.panel.MenuPanel
import com.example.abasgo.ui.panel.RoulettePanel
import com.example.abasgo.ui.state.AppPanel
import com.example.abasgo.ui.state.FavouriteUIState
import com.example.abasgo.ui.viewmodel.FavouriteViewModel
import com.example.abasgo.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ABASgoApplication : Application()

@AndroidEntryPoint
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
fun ABASgoApp() {
    val viewModel: MainViewModel = viewModel()

    SystemBars(
        statusBarColor = DarkGreen,
        navigationBarColor = DarkGreen,
        darkIcons = false
    )

    val currentPanel = viewModel.getCurrentPanel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                current = currentPanel,
                onSelect = { viewModel.replacePanel(it) },
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 12.dp),
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
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            val panelModifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .padding(vertical = 12.dp)

            when (currentPanel) {
                AppPanel.FAVORITE -> {
                    val vm: FavouriteViewModel = hiltViewModel()
                    val uiState by vm.uiState.collectAsStateWithLifecycle()

                    FavouritePanel(
                        modifier = panelModifier,
                        uiState = uiState,
                        onEvent = { event -> vm.onEvent(event) }
                    )
                }
                AppPanel.HISTORY -> HistoryPanel(panelModifier)
                AppPanel.ROULETTE -> RoulettePanel(panelModifier)
                AppPanel.MENU -> MenuPanel(panelModifier)
                AppPanel.MAP -> {}
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun ABASgoAppPreview() {
    ABASgoTheme {
        ABASgoAppPreviewContent()
    }
}

@Composable
private fun ABASgoAppPreviewContent() {
    SystemBars(
        statusBarColor = DarkGreen,
        navigationBarColor = DarkGreen,
        darkIcons = false
    )

    val viewModel: MainViewModel = viewModel()
    val currentPanel = viewModel.getCurrentPanel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                current = currentPanel,
                onSelect = { viewModel.replacePanel(it) },
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 12.dp),
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
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            val panelModifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .padding(vertical = 12.dp)

            when (currentPanel) {
                AppPanel.FAVORITE -> {

                    FavouritePanel(
                        modifier = panelModifier,
                        uiState = FavouriteUIState.Success(places = emptyList()),
                        onEvent = { }
                    )
                }
                AppPanel.HISTORY -> HistoryPanel(panelModifier)
                AppPanel.ROULETTE -> RoulettePanel(panelModifier)
                AppPanel.MENU -> MenuPanel(panelModifier)
                AppPanel.MAP -> {}
            }
        }
    }
}
