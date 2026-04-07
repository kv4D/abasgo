package com.example.abasgo

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.abasgo.ui.theme.ABASgoTheme
import com.example.abasgo.ui.theme.DarkGreen
import com.example.abasgo.ui.SystemBars
import com.example.abasgo.ui.component.NavigationBar
import com.example.abasgo.ui.component.SearchBar
import com.example.abasgo.ui.AppRoute
import com.example.abasgo.ui.panel.FavouritePanel
import com.example.abasgo.ui.panel.HistoryPanel
import com.example.abasgo.ui.panel.MenuPanel
import com.example.abasgo.ui.panel.RoulettePanel
import com.example.abasgo.ui.panel.VisitedPlaceDetailPanel
import com.example.abasgo.ui.panel.VisitedPlaceEditPanel
import com.example.abasgo.ui.viewmodel.FavouriteViewModel
import com.example.abasgo.ui.viewmodel.VisitedViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.abasgo.ui.getCurrentAppRoute
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.material3.CompassButton
import org.maplibre.compose.material3.ExpandingAttributionButton
import org.maplibre.compose.material3.ScaleBar
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.style.rememberStyleState
import org.maplibre.spatialk.geojson.Position


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
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentAppRoute = getCurrentAppRoute(navBackStackEntry)

    SystemBars(
        statusBarColor = DarkGreen,
        navigationBarColor = DarkGreen,
        darkIcons = false
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                current = currentAppRoute,
                onSelect = { appRoute ->
                    navController.navigate(appRoute.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
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
        Box(modifier = Modifier.fillMaxSize()) {
            val cameraState = rememberCameraState()
            val styleState = rememberStyleState()
            MaplibreMap(
                baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty"),
                cameraState = cameraState,
                styleState = styleState,
                options = MapOptions(ornamentOptions = OrnamentOptions.OnlyLogo),
            )

            Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                ScaleBar(
                    cameraState.metersPerDpAtTarget,
                    modifier = Modifier.align(Alignment.TopStart)
                )
                CompassButton(
                    cameraState,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
                ExpandingAttributionButton(
                    cameraState = cameraState,
                    styleState = styleState,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    contentAlignment = Alignment.BottomEnd,
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                val panelModifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 12.dp)

                // Register new routes here
                NavHost(
                    navController = navController,
                    startDestination = AppRoute.Map.route,
                ) {
                    composable(AppRoute.Map.route) {
                    }

                    composable(AppRoute.Favourite.route) {
                        val vm: FavouriteViewModel = hiltViewModel()
                        val uiState by vm.uiState.collectAsStateWithLifecycle()

                        FavouritePanel(
                            modifier = panelModifier,
                            uiState = uiState,
                            onEvent = { event -> vm.onEvent(event) }
                        )
                    }

                    composable(AppRoute.History.route) {
                        val vm: VisitedViewModel = hiltViewModel()
                        val uiState by vm.uiState.collectAsStateWithLifecycle()

                        HistoryPanel(
                            modifier = panelModifier,
                            uiState = uiState,
                            onEvent = { event -> vm.onEvent(event) },
                            onSelectPlace = { placeId ->
                                navController.navigate(AppRoute.HistoryDetail.create(placeId))
                            }
                        )
                    }

                    composable(AppRoute.Roulette.route) { RoulettePanel(panelModifier) }
                    composable(AppRoute.Menu.route) { MenuPanel(panelModifier) }

                    composable(
                        route = AppRoute.HistoryDetail.route,
                        arguments = listOf(
                            navArgument(
                                AppRoute.HistoryDetail.ARG_PLACE_ID
                            ) {
                                type = NavType.LongType
                            }
                        )
                    ) {
                        VisitedPlaceDetailPanel(
                            modifier = panelModifier,
                            onEditClick = { placeId ->
                                navController.navigate(AppRoute.HistoryEdit.create(placeId))
                            }
                        )
                    }

                    composable(
                        route = AppRoute.HistoryEdit.route,
                        arguments = listOf(
                            navArgument(AppRoute.HistoryEdit.ARG_PLACE_ID) { type = NavType.LongType }
                        )
                    ) {
                        VisitedPlaceEditPanel(
                            modifier = panelModifier,
                            onCancel = { navController.navigateUp() },
                            onSaved = { navController.navigateUp() }
                        )
                    }
                }
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
    ABASgoApp()
}
