package br.com.victorcs.weatherview.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDescriptionResponse(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)