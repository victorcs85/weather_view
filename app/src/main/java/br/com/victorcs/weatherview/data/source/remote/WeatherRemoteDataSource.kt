package br.com.victorcs.weatherview.data.source.remote

import br.com.victorcs.weatherview.BuildConfig
import br.com.victorcs.weatherview.data.entity.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val WEATHER_API_URL = "https://api.openweathermap.org/data/3.0/"
private const val LAT = "lat"
private const val LON = "lon"
private const val APP_ID = "appid"

class WeatherRemoteDataSource(
    private val client: HttpClient
) : WeatherAPI {

    override suspend fun getWeather(
        lat: Double,
        lon: Double,
    ): WeatherResponse {
        return client.get(WEATHER_API_URL) {
            parameter(LAT, lat)
            parameter(LON, lon)
            parameter(APP_ID, BuildConfig.WEATHER_KEY)
        }.body()
    }

}