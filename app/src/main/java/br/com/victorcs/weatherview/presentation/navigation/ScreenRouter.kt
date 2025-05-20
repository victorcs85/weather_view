package br.com.victorcs.weatherview.presentation.navigation

private const val WEATHER = "weather"

sealed class ScreenRouter(val route: String) {
    object WeatherScreen : ScreenRouter(WEATHER)
}
