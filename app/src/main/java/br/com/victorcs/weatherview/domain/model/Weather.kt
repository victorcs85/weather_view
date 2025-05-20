package br.com.victorcs.weatherview.domain.model

data class Weather(
    val coordinates: Coordinates,
    val weatherInfo: List<WeatherInfo>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val rainVolume: Double?,
    val clouds: Clouds,
    val timestamp: Long,
    val system: SystemInfo,
    val timezone: Int,
    val locationId: Long,
    val locationName: String,
    val statusCode: Int
)

data class Coordinates(val lon: Double, val lat: Double)

data class WeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
    val seaLevel: Int?,
    val groundLevel: Int?
)

data class Wind(
    val speed: Double,
    val degree: Int,
    val gust: Double?
)

data class Clouds(val coverage: Int)

data class SystemInfo(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)