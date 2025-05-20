package br.com.victorcs.weatherview.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val coord: CoordResponse,
    val weather: List<WeatherInfoResponse>,
    val base: String,
    val main: MainResponse,
    val visibility: Int,
    val wind: WindResponse,
    val rain: RainResponse?,
    val clouds: CloudsResponse,
    val dt: Long,
    val sys: SysResponse,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int
)

@Serializable
data class CoordResponse(
    val lon: Double,
    val lat: Double
)

@Serializable
data class WeatherInfoResponse(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class MainResponse(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int?,
    val grnd_level: Int?
)

@Serializable
data class WindResponse(
    val speed: Double,
    val deg: Int,
    val gust: Double?
)

@Serializable
data class RainResponse(
    @JvmField val `1h`: Double
)

@Serializable
data class CloudsResponse(
    val all: Int
)

@Serializable
data class SysResponse(
    val type: Int?,
    val id: Int?,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)