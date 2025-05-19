package br.com.victorcs.weatherview.presentation.features.weather.state

import br.com.victorcs.weatherview.domain.model.Weather

data class WeatherState(
    val isLoading: Boolean = false,
    val weathers: List<Weather> = emptyList(),
    val error: String? = null
)
