package com.example.weatherforecastapplication.data.repo.remote

import com.example.weatherforecastapplication.data.model.Current
import com.example.weatherforecastapplication.data.model.WeatherItem
import com.example.weatherforecastapplication.data.model.WeatherResponse

class FakeWeatherRemote:WeatherRemoteSource {
    override suspend fun getWeatherForecast(
        lat: Double,
        lon: Double,
        lang: String
    ): WeatherResponse {
        return WeatherResponse(
            current = Current(
                sunrise = 1617857580,
                temp = 280.32,
                visibility = 10000,
                uvi = 0.32,
                pressure = 1012,
                clouds = 75,
                feelsLike = 275.15,
                dt = 1617860400,
                windDeg = 360,
                sunset = 1617902360,
                weather = listOf(
                    WeatherItem(
                        icon = "01d",
                        description = "Clear",
                        main = "Clear",
                        id = 800
                    )
                ),
                humidity = 93,
                windSpeed = 3.09
            ),
            timezone = "Europe/London",
            timezoneOffset = 3600,
            daily = null,
            lon = lon,
            hourly = null,
            minutely = null,
            lat = lat
        )
    }
}