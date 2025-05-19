package br.com.victorcs.weatherview.domain.model

data class Weather(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezoneOffset: Int,
    val current: CurrentWeather
)
