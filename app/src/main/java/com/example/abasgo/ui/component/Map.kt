package com.example.abasgo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import android.util.Log
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import org.json.JSONArray
import org.json.JSONObject
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.style.rememberStyleState
import org.maplibre.compose.util.ClickResult
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.unit.dp
import java.net.HttpURLConnection
import java.net.URLEncoder
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onStart
import org.maplibre.spatialk.geojson.BoundingBox
import org.maplibre.spatialk.geojson.Position

private data class MapPoi(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val longitude: Double,
    val latitude: Double,
    val osmType: String,
    val osmId: Long,
)

private data class PlaceRemoteInfo(
    val displayName: String,
    val category: String,
    val source: String,
    val temperatureC: Double?,
)

private sealed interface RemoteInfoState {
    data object Idle : RemoteInfoState
    data object Loading : RemoteInfoState
    data class Success(val info: PlaceRemoteInfo) : RemoteInfoState
    data class Error(val message: String) : RemoteInfoState
}

private data class PoiLoadState(
    val pois: List<MapPoi>,
    val status: String,
    val isFallback: Boolean,
)

private const val KRASNOYARSK_LAT = 56.0153
private const val KRASNOYARSK_LON = 92.8932
private const val KRASNOYARSK_SEARCH_RADIUS_METERS = 35_000
private const val MAPLIBRE_STYLE_URI = "https://basemaps.cartocdn.com/gl/positron-gl-style/style.json"
private const val POI_LOG_TAG = "POI"
private val OVERPASS_ENDPOINTS = listOf(
    "https://overpass-api.de/api/interpreter",
    "https://overpass.osm.ch/api/interpreter",
    "https://overpass.kumi.systems/api/interpreter",
    "https://overpass.openstreetmap.ru/api/interpreter",
)

private val fallbackKrasnoyarskPois = listOf(
    MapPoi(
        id = "fallback-1",
        title = "National Park Krasnoyarsk Stolby",
        category = "Nature / Hiking",
        description = "Popular hiking area with rock formations and trails.",
        longitude = 92.7456,
        latitude = 55.9415,
        osmType = "fallback",
        osmId = 1,
    ),
    MapPoi(
        id = "fallback-2",
        title = "Tatyshev Island",
        category = "Leisure / Park",
        description = "Large island park for walks, bike routes, and city recreation.",
        longitude = 92.9444,
        latitude = 56.0224,
        osmType = "fallback",
        osmId = 2,
    ),
    MapPoi(
        id = "fallback-3",
        title = "Paraskeva Pyatnitsa Chapel",
        category = "Historic / Viewpoint",
        description = "City viewpoint and landmark overlooking Krasnoyarsk.",
        longitude = 92.8662,
        latitude = 56.0185,
        osmType = "fallback",
        osmId = 3,
    ),
)

@Composable
@OptIn(FlowPreview::class, ExperimentalMaterial3Api::class)
fun ABASgoMap() {
    var selectedPoi by remember { mutableStateOf<MapPoi?>(null) }
    val cameraState = rememberCameraState(
        firstPosition = CameraPosition(
            target = Position(longitude = KRASNOYARSK_LON, latitude = KRASNOYARSK_LAT),
            zoom = 11.2,
        )
    )
    val styleState = rememberStyleState()
    var poiLoadState by remember {
        mutableStateOf(
            PoiLoadState(
                pois = fallbackKrasnoyarskPois,
                status = "Loading POI for visible area...",
                isFallback = true,
            )
        )
    }
    var lastPoiQueryKey by remember { mutableStateOf<String?>(null) }
    val pois = poiLoadState.pois
    val poiGeoJson = remember(pois) { buildPoiGeoJsonString(pois) }
    val style = BaseStyle.Uri(MAPLIBRE_STYLE_URI)
    var remoteInfoState by remember { mutableStateOf<RemoteInfoState>(RemoteInfoState.Idle) }

    LaunchedEffect(pois) {
        if (pois.none { it.id == selectedPoi?.id }) {
            selectedPoi = null
        }
    }

    LaunchedEffect(Unit) {
        cameraState.position = CameraPosition(
            target = Position(longitude = KRASNOYARSK_LON, latitude = KRASNOYARSK_LAT),
            zoom = 11.2,
        )

        suspend fun reloadPois(force: Boolean = false): Boolean {
            val projection = cameraState.projection ?: return false
            val bbox = projection.queryVisibleBoundingBox().clampForPoiSearch()
            val queryKey = bbox.queryKey()
            if (!force && queryKey == lastPoiQueryKey) return true
            lastPoiQueryKey = queryKey

            val loaded = loadPoisForBoundingBox(bbox)
            Log.i(POI_LOG_TAG, "bbox=$queryKey loaded=${loaded.pois.size} fallback=${loaded.isFallback} status=${loaded.status}")
            poiLoadState = if (loaded.pois.isEmpty()) {
                PoiLoadState(
                    pois = poiLoadState.pois,
                    status = loaded.status,
                    isFallback = poiLoadState.isFallback,
                )
            } else {
                loaded
            }
            return true
        }

        repeat(60) {
            if (reloadPois(force = true)) return@repeat
            delay(250)
        }

        launch {
            while (true) {
                delay(12_000)
                if (poiLoadState.isFallback && !cameraState.isCameraMoving) {
                    reloadPois(force = true)
                }
            }
        }

        snapshotFlow { cameraState.isCameraMoving }
            .onStart { emit(false) }
            .distinctUntilChanged()
            .debounce(500)
            .filter { moving -> !moving }
            .collect {
                var loaded = reloadPois(force = poiLoadState.isFallback)
                if (!loaded) {
                    repeat(20) {
                        delay(250)
                        loaded = reloadPois(force = true)
                        if (loaded) return@repeat
                    }
                }
            }
    }

    LaunchedEffect(selectedPoi?.id) {
        val poi = selectedPoi
        if (poi == null) {
            remoteInfoState = RemoteInfoState.Idle
            return@LaunchedEffect
        }

        remoteInfoState = RemoteInfoState.Loading
        remoteInfoState = runCatching {
            RemoteInfoState.Success(fetchRemoteInfoForPoi(poi))
        }.getOrElse {
            RemoteInfoState.Error("Failed to load place details from API")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MaplibreMap(
            baseStyle = style,
            cameraState = cameraState,
            styleState = styleState,
            options = MapOptions(ornamentOptions = OrnamentOptions.AllDisabled),
            onMapClick = { _, _ ->
                selectedPoi = null
                ClickResult.Pass
            }
        ) {
            val poiSource = rememberGeoJsonSource(data = GeoJsonData.JsonString(poiGeoJson))

            CircleLayer(
                id = "poi_markers",
                source = poiSource,
                color = const(Color(0xFFE24A4A)),
                radius = const(10.dp),
                strokeColor = const(Color.White),
                strokeWidth = const(3.dp),
                onClick = { features ->
                    val id = features
                        .firstOrNull()
                        ?.properties
                        ?.get("id")
                        ?.jsonPrimitive
                        ?.contentOrNull

                    selectedPoi = pois.firstOrNull { it.id == id }
                    if (selectedPoi != null) ClickResult.Consume else ClickResult.Pass
                }
            )
        }
    }

    selectedPoi?.let { poi ->
        ModalBottomSheet(
            onDismissRequest = { selectedPoi = null },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = poi.title,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    IconButton(onClick = { selectedPoi = null }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close place details",
                        )
                    }
                }

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                ) {
                    Text(
                        text = poi.category,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }

                Text(
                    text = poi.description,
                    style = MaterialTheme.typography.bodyMedium,
                )

                when (val state = remoteInfoState) {
                    RemoteInfoState.Idle -> {
                    }
                    RemoteInfoState.Loading -> {
                        Text("Загрузка",
                                style = MaterialTheme.typography.labelLarge,)
                    }
                    is RemoteInfoState.Error -> {
                        InfoRow(label = "Ошибка", value = state.message)
                    }
                    is RemoteInfoState.Success -> {
                        InfoRow(label = "Данные", value = state.info.displayName)
                        InfoRow(label = "Категория", value = poi.category)
                        InfoRow(
                            label = "Погода",
                            value = state.info.temperatureC?.let { "${"%.1f".format(it)}°C" } ?: "Нет данных о погоде",
                        )
                    }
                }

                InfoRow(label = "Отзывы", value = "ПОЗЖЕ")
            }
        }
    }
}

private fun buildPoiGeoJsonString(pois: List<MapPoi>): String {
    val features = JSONArray()
    pois.forEach { poi ->
        val feature = JSONObject()
        feature.put("type", "Feature")

        val geometry = JSONObject()
        geometry.put("type", "Point")
        geometry.put("coordinates", JSONArray().put(poi.longitude).put(poi.latitude))

        val properties = JSONObject()
        properties.put("id", poi.id)
        properties.put("title", poi.title)
        properties.put("category", poi.category)

        feature.put("geometry", geometry)
        feature.put("properties", properties)
        features.put(feature)
    }

    return JSONObject()
        .put("type", "FeatureCollection")
        .put("features", features)
        .toString()
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

private suspend fun loadPoisForBoundingBox(bbox: BoundingBox): PoiLoadState {
    val loadedResult = runCatching { fetchKrasnoyarskPois(bbox) }
    val loaded = loadedResult.getOrElse { throwable ->
        return PoiLoadState(
            pois = fallbackKrasnoyarskPois,
            status = "Overpass error: ${throwable.message ?: throwable::class.simpleName.orEmpty()}. Auto retry in 12s",
            isFallback = true,
        )
    }

    return if (loaded.isNotEmpty()) {
        PoiLoadState(
            pois = loaded,
            status = "POI loaded for visible area: ${loaded.size}",
            isFallback = false,
        )
    } else {
        PoiLoadState(
            pois = emptyList(),
            status = "No POI found in visible area. Try zooming in.",
            isFallback = false,
        )
    }
}

private suspend fun fetchKrasnoyarskPois(bbox: BoundingBox): List<MapPoi> {
    val south = bbox.southwest.latitude
    val west = bbox.southwest.longitude
    val north = bbox.northeast.latitude
    val east = bbox.northeast.longitude

    val query = """
        [out:json][timeout:25];
        (
          nwr($south,$west,$north,$east)[tourism~"attraction|museum|viewpoint|zoo|gallery"];
          nwr($south,$west,$north,$east)[leisure~"park|garden|nature_reserve"];
          nwr($south,$west,$north,$east)[historic];
          nwr($south,$west,$north,$east)[natural~"peak|cliff|wood|water"];
        );
                out center 50;
    """.trimIndent()

    val encodedQuery = URLEncoder.encode(query, Charsets.UTF_8.name())
    val response = run {
        var lastError: Throwable? = null
        var responseBody: String? = null

        for (endpoint in OVERPASS_ENDPOINTS) {
            val result = runCatching {
                httpPostForm(
                    url = endpoint,
                    body = "data=$encodedQuery",
                    connectTimeoutMs = 4_000,
                    readTimeoutMs = 7_000,
                )
            }

            if (result.isSuccess) {
                responseBody = result.getOrNull()
                break
            }
            lastError = result.exceptionOrNull()
        }

        responseBody ?: throw IllegalStateException(
            "Overpass unavailable: ${lastError?.message ?: "unknown error"}"
        )
    }
    val root = JSONObject(response)
    val elements = root.optJSONArray("elements") ?: return emptyList()
    val dedup = linkedMapOf<String, MapPoi>()

    for (index in 0 until elements.length()) {
        val node = elements.optJSONObject(index) ?: continue
        val tags = node.optJSONObject("tags") ?: continue
        val name = tags.optString("name", "").trim()
        if (name.isBlank()) continue

        val lat = when {
            node.has("lat") -> node.optDouble("lat", Double.NaN)
            node.has("center") -> node.optJSONObject("center")?.optDouble("lat", Double.NaN) ?: Double.NaN
            else -> Double.NaN
        }
        val lon = when {
            node.has("lon") -> node.optDouble("lon", Double.NaN)
            node.has("center") -> node.optJSONObject("center")?.optDouble("lon", Double.NaN) ?: Double.NaN
            else -> Double.NaN
        }
        if (!lat.isFinite() || !lon.isFinite()) continue

        val osmType = node.optString("type", "unknown")
        val osmId = node.optLong("id", -1L)
        if (osmId <= 0L) continue

        val category = when {
            tags.has("tourism") -> "Туристическое место"
            tags.has("leisure") -> "Место досуга"
            tags.has("historic") -> "Историческое место"
            tags.has("natural") -> "Природное место"
            else -> "Интересное место"
        }
        val description = tags.optString("description", "Описание")

        val id = "$osmType-$osmId"
        dedup[id] = MapPoi(
            id = id,
            title = name,
            category = category,
            description = description,
            longitude = lon,
            latitude = lat,
            osmType = osmType,
            osmId = osmId,
        )
    }

    return dedup.values.sortedBy { it.title }
}

private fun BoundingBox.clampForPoiSearch(): BoundingBox {
    val centerLat = (southwest.latitude + northeast.latitude) / 2.0
    val centerLon = (southwest.longitude + northeast.longitude) / 2.0
    val halfLat = minOf((northeast.latitude - southwest.latitude) / 2.0, 0.04)
    val halfLon = minOf((northeast.longitude - southwest.longitude) / 2.0, 0.10)

    return BoundingBox(
        southwest = Position(longitude = centerLon - halfLon, latitude = centerLat - halfLat),
        northeast = Position(longitude = centerLon + halfLon, latitude = centerLat + halfLat),
    )
}

private fun BoundingBox.queryKey(): String =
    listOf(
        southwest.latitude,
        southwest.longitude,
        northeast.latitude,
        northeast.longitude,
    ).joinToString(separator = ":") { value -> "%.3f".format(value) }

private suspend fun fetchRemoteInfoForPoi(poi: MapPoi): PlaceRemoteInfo {
    val nominatimUrl = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${poi.latitude}&lon=${poi.longitude}"
    val reverseJson = JSONObject(httpGet(nominatimUrl))
    val displayName = reverseJson.optString("display_name", poi.title)
    val category = "${reverseJson.optString("category", "unknown")} / ${reverseJson.optString("type", "unknown")}" 

    val weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=${poi.latitude}&longitude=${poi.longitude}&current=temperature_2m&timezone=auto"
    val weatherJson = JSONObject(httpGet(weatherUrl))
    val temp = weatherJson.optJSONObject("current")?.optDouble("temperature_2m", Double.NaN)
    val temperature = temp?.takeIf { it.isFinite() }

    return PlaceRemoteInfo(
        displayName = displayName,
        category = category,
        source = "Nominatim + Open-Meteo",
        temperatureC = temperature,
    )
}

private suspend fun httpGet(url: String): String =
    withContext(Dispatchers.IO) {
        val connection = (URL(url).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 12_000
            readTimeout = 12_000
            setRequestProperty("User-Agent", "ABASgo/1.0")
        }

        try {
            val code = connection.responseCode
            val stream = if (code in 200..299) connection.inputStream else connection.errorStream
            val body = stream?.bufferedReader()?.use { it.readText() }.orEmpty()
            if (code !in 200..299) {
                throw IllegalStateException("HTTP $code: $body")
            }
            body
        } finally {
            connection.disconnect()
        }
    }

private suspend fun httpPostForm(
    url: String,
    body: String,
    connectTimeoutMs: Int = 20_000,
    readTimeoutMs: Int = 35_000,
): String =
    withContext(Dispatchers.IO) {
        val connection = (URL(url).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            doOutput = true
            connectTimeout = connectTimeoutMs
            readTimeout = readTimeoutMs
            setRequestProperty("User-Agent", "ABASgo/1.0")
            setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
        }

        try {
            connection.outputStream.use { output ->
                output.write(body.toByteArray(Charsets.UTF_8))
            }

            val code = connection.responseCode
            val stream = if (code in 200..299) connection.inputStream else connection.errorStream
            val responseBody = stream?.bufferedReader()?.use { it.readText() }.orEmpty()
            if (code !in 200..299) {
                throw IllegalStateException("HTTP $code: $responseBody")
            }
            responseBody
        } finally {
            connection.disconnect()
        }
    }