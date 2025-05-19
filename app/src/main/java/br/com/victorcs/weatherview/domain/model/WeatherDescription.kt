package br.com.victorcs.weatherview.domain.model

data class WeatherDescription(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
