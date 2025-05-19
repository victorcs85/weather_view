package br.com.victorcs.weatherview.data.source.remote

import br.com.victorcs.weatherview.data.entity.WeatherResponse

interface WeatherAPI {
    suspend fun getWeather(
        lat: Double,
        lon: Double,
    ): WeatherResponse
}