package br.com.victorcs.weatherview.domain.repository

import br.com.victorcs.weatherview.data.entity.WeatherResponse
import br.com.victorcs.weatherview.domain.model.Response

interface IWeatherRepository {
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): Response<WeatherResponse>
}