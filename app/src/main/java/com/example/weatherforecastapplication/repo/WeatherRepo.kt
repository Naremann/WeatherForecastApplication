package com.example.weatherforecastapplication.repo


import com.example.weatherforecastapplication.model.WeatherResponse
import javax.inject.Inject

interface WeatherRepo {
    suspend fun getWeatherForecast(lat:Double,lon:Double): WeatherResponse
    class WeatherRepoImp @Inject constructor(private val weatherRemoteSource:WeatherRemoteSource):WeatherRepo{
        override suspend fun getWeatherForecast(lat: Double,lon: Double): WeatherResponse {
           return weatherRemoteSource.getWeatherForecast(lat,lon)
        }

    }

}
