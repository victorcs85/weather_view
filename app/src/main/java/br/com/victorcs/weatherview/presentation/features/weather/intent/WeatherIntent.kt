package br.com.victorcs.weatherview.presentation.features.weather.intent

sealed class WeatherIntent {
    object FetchConcurrently : WeatherIntent()
}
