package com.example.abasgo.ui

import androidx.navigation.NavBackStackEntry

sealed class AppRoute(val route: String) {
    data object Map : AppRoute("map")
    data object Favourite : AppRoute("favourite")
    data object History : AppRoute("history")
    data object Roulette : AppRoute("roulette")
    data object Menu : AppRoute("menu")

    data object HistoryDetail : AppRoute("history/detail/{placeId}") {
        fun create(placeId: Long) = "history/detail/$placeId"
        const val ARG_PLACE_ID: String = "placeId"
    }

    data object HistoryEdit : AppRoute("history/edit/{placeId}") {
        fun create(placeId: Long) = "history/edit/$placeId"
        const val ARG_PLACE_ID: String = "placeId"
    }
}

fun getCurrentAppRoute(navBackStackEntry: NavBackStackEntry?): AppRoute {
    val route = navBackStackEntry?.destination?.route ?: return AppRoute.Map
    return when {
        route == AppRoute.Map.route -> AppRoute.Map
        route == AppRoute.Favourite.route -> AppRoute.Favourite
        route == AppRoute.History.route -> AppRoute.History
        route == AppRoute.Roulette.route -> AppRoute.Roulette
        route == AppRoute.Menu.route -> AppRoute.Menu
        route.startsWith("history/detail/") -> AppRoute.HistoryDetail
        route.startsWith("history/edit/") -> AppRoute.HistoryEdit
        else -> AppRoute.Map
    }
}

fun getNavigationRoutes(): List<AppRoute> {
    return listOf(
        AppRoute.Favourite,
        AppRoute.History,
        AppRoute.Roulette,
        AppRoute.Menu
    )
}

fun hasCommonBaseRoute(route1: AppRoute, route2: AppRoute): Boolean {
    return route1
        .route.substringBefore("/")== route2.route.substringBefore("/")
}

