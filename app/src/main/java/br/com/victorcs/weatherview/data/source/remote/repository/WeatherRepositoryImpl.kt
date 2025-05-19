package br.com.victorcs.weatherview.data.source.remote.repository

import br.com.victorcs.weatherview.data.entity.WeatherResponse
import br.com.victorcs.weatherview.data.source.remote.WeatherRemoteDataSource
import br.com.victorcs.weatherview.data.source.remote.safeApiCall
import br.com.victorcs.weatherview.domain.mapper.DomainMapper
import br.com.victorcs.weatherview.domain.model.Response
import br.com.victorcs.weatherview.domain.model.Weather
import br.com.victorcs.weatherview.domain.repository.IWeatherRepository

class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val mapper: DomainMapper<WeatherResponse, Weather>
) : IWeatherRepository {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): Response<WeatherResponse> = safeApiCall {
        remoteDataSource.getWeather(latitude, longitude).also { response ->
            mapper.toDomain(response)
        }
    }
}
