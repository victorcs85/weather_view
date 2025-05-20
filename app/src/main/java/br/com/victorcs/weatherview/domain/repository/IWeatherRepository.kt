package br.com.victorcs.weatherview.domain.repository

import br.com.victorcs.weatherview.domain.model.Response
import br.com.victorcs.weatherview.domain.model.Weather

interface IWeatherRepository {
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): Response<Weather>
}