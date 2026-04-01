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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abasgo.ui.theme.ABASgoTheme
import com.example.abasgo.ui.theme.DarkGreen
import com.example.abasgo.ui.SystemBars
import com.example.abasgo.ui.component.NavigationBar
import com.example.abasgo.ui.component.SearchBar
import com.example.abasgo.ui.panel.FavoritePanel
import com.example.abasgo.ui.panel.HistoryPanel
import com.example.abasgo.ui.panel.MenuPanel
import com.example.abasgo.ui.panel.RoulettePanel
import com.example.abasgo.ui.state.AppPanel
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

@PreviewScreenSizes
@Composable
fun ABASgoApp(viewModel:  MainViewModel = viewModel()) {

    SystemBars(
        statusBarColor = DarkGreen,
        navigationBarColor = DarkGreen,
        darkIcons = false
    )

    val state = viewModel.state
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
            val modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .padding(vertical = 12.dp)

            when (currentPanel) {
                AppPanel.FAVORITE -> FavoritePanel(modifier)
                AppPanel.HISTORY -> HistoryPanel(modifier)
                AppPanel.ROULETTE -> RoulettePanel(modifier)
                AppPanel.MENU -> MenuPanel(modifier)
                AppPanel.MAP -> {}
            }
        }
    }
}
