package br.com.victorcs.weatherview.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponse(
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val wind_gust: Double? = null,
    val weather: List<WeatherDescriptionResponse>
)
