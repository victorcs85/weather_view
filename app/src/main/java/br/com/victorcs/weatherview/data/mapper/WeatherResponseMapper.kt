package br.com.victorcs.weatherview.data.mapper

import br.com.victorcs.weatherview.data.entity.CloudsResponse
import br.com.victorcs.weatherview.data.entity.CoordResponse
import br.com.victorcs.weatherview.data.entity.MainResponse
import br.com.victorcs.weatherview.data.entity.SysResponse
import br.com.victorcs.weatherview.data.entity.WeatherInfoResponse
import br.com.victorcs.weatherview.data.entity.WeatherResponse
import br.com.victorcs.weatherview.data.entity.WindResponse
import br.com.victorcs.weatherview.domain.mapper.DomainMapper
import br.com.victorcs.weatherview.domain.model.Clouds
import br.com.victorcs.weatherview.domain.model.Coordinates
import br.com.victorcs.weatherview.domain.model.Main
import br.com.victorcs.weatherview.domain.model.SystemInfo
import br.com.victorcs.weatherview.domain.model.Weather
import br.com.victorcs.weatherview.domain.model.WeatherInfo
import br.com.victorcs.weatherview.domain.model.Wind

class WeatherResponseMapper() : DomainMapper<WeatherResponse, Weather> {
    override fun toDomain(from: WeatherResponse) = Weather(
        coordinates = from.coord.toDomain(),
        weatherInfo = from.weather.map { it.toDomain() },
        base = from.base,
        main = from.main.toDomain(),
        visibility = from.visibility,
        wind = from.wind.toDomain(),
        rainVolume = from.rain?.`1h`,
        clouds = from.clouds.toDomain(),
        timestamp = from.dt,
        system = from.sys.toDomain(),
        timezone = from.timezone,
        locationId = from.id,
        locationName = from.name,
        statusCode = from.cod
    )

    private fun CoordResponse.toDomain() = Coordinates(
        lon = lon,
        lat = lat
    )

    private fun WeatherInfoResponse.toDomain() = WeatherInfo(
        id = id,
        main = main,
        description = description,
        icon = icon
    )

    private fun MainResponse.toDomain() = Main(
        temp = temp,
        feelsLike = feels_like,
        tempMin = temp_min,
        tempMax = temp_max,
        pressure = pressure,
        humidity = humidity,
        seaLevel = sea_level,
        groundLevel = grnd_level
    )

    private fun WindResponse.toDomain() = Wind(
        speed = speed,
        degree = deg,
        gust = gust
    )

    private fun CloudsResponse.toDomain() = Clouds(
        coverage = all
    )

    private fun SysResponse.toDomain() = SystemInfo(
        type = type ?: 0,
        id = id ?: 0,
        country = country,
        sunrise = sunrise,
        sunset = sunset
    )
}