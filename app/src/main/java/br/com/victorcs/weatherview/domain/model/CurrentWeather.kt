package br.com.victorcs.weatherview.domain.model

data class CurrentWeather(
    val dateTime: String,
    val sunrise: String?,
    val sunset: String?,
    val tempCelsius: Double,
    val feelsLikeCelsius: Double,
    val pressure: Int,
    val humidity: Int,
    val dewPointCelsius: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val windGust: Double?,
    val weather: List<WeatherDescription>
)
