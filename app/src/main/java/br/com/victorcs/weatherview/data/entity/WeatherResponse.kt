package br.com.victorcs.weatherview.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: CurrentWeatherResponse
)
