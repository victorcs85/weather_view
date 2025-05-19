package br.com.victorcs.weatherview.data.mapper

import br.com.victorcs.weatherview.data.entity.CurrentWeatherResponse
import br.com.victorcs.weatherview.data.entity.WeatherDescriptionResponse
import br.com.victorcs.weatherview.data.entity.WeatherResponse
import br.com.victorcs.weatherview.domain.mapper.DomainMapper
import br.com.victorcs.weatherview.domain.model.CurrentWeather
import br.com.victorcs.weatherview.domain.model.Weather
import br.com.victorcs.weatherview.domain.model.WeatherDescription
import br.com.victorcs.weatherview.extensions.toCelsius
import br.com.victorcs.weatherview.extensions.toFormattedDate

class WeatherResponseMapper() : DomainMapper<WeatherResponse, Weather> {
    override fun toDomain(from: WeatherResponse) = Weather(
        lat = from.lat,
        lon = from.lon,
        timezone = from.timezone,
        timezoneOffset = from.timezone_offset,
        current = from.current.toCurrentWeather()
    )

    private fun CurrentWeatherResponse.toCurrentWeather() = CurrentWeather(
        dateTime = this.dt.toFormattedDate(),
        sunrise = this.sunrise?.toFormattedDate(),
        sunset = this.sunset?.toFormattedDate(),
        tempCelsius = this.temp.toCelsius(),
        feelsLikeCelsius = this.feels_like.toCelsius(),
        pressure = this.pressure,
        humidity = this.humidity,
        dewPointCelsius = this.dew_point.toCelsius(),
        uvi = this.uvi,
        clouds = this.clouds,
        visibility = this.visibility,
        windSpeed = this.wind_speed,
        windDeg = this.wind_deg,
        windGust = this.wind_gust,
        weather = this.weather.toWeatherDescription(),
    )

    private fun List<WeatherDescriptionResponse>.toWeatherDescription()= this.map {
        WeatherDescription(
            id = it.id,
            main = it.main,
            description = it.description,
            icon = it.icon
        )
    }
}