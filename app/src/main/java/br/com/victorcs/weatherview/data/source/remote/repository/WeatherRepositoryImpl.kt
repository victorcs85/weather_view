package br.com.victorcs.weatherview.data.source.remote.repository

import br.com.victorcs.weatherview.data.entity.WeatherResponse
import br.com.victorcs.weatherview.data.source.remote.WeatherAPI
import br.com.victorcs.weatherview.data.source.remote.safeApiCall
import br.com.victorcs.weatherview.domain.mapper.DomainMapper
import br.com.victorcs.weatherview.domain.model.Response
import br.com.victorcs.weatherview.domain.model.Weather
import br.com.victorcs.weatherview.domain.repository.IWeatherRepository

class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherAPI,
    private val mapper: DomainMapper<WeatherResponse, Weather>
) : IWeatherRepository {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): Response<Weather> = safeApiCall {
        val response = remoteDataSource.getWeather(latitude, longitude)
        mapper.toDomain(response)
    }
}
