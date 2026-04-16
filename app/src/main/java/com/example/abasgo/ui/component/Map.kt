package com.example.abasgo.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.abasgo.R
import com.google.common.io.Resources
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.style.rememberStyleState
import org.maplibre.compose.util.ClickResult
import androidx.compose.ui.platform.LocalResources

@Composable
fun ABASgoMap() {
    /* val locationState = rememberUserLocationState(
        locationProvider = rememberAndroidLocationProvider(
            updateInterval = 3.seconds,
            desiredAccuracy = DesiredAccuracy.Balanced,
            minDistanceMeters = 5.toFloat()
        )
    )
     */
    val cameraState = rememberCameraState()
    val styleState = rememberStyleState()
    val style = BaseStyle.Json(LocalResources.current.openRawResource(R.raw.tiles).bufferedReader().use { it.readText() })
    MaplibreMap(
        baseStyle = style,
        cameraState = cameraState,
        styleState = styleState,
        options = MapOptions(ornamentOptions = OrnamentOptions.AllDisabled),
        onMapClick = { point, screenPoint -> ClickResult.Pass}
    ) {
        /* LocationPuck(
            locationState = locationState,
            cameraState = cameraState,
            idPrefix = "what"
        )
         */
    }
}