package com.example.weatherforecastapplication.repo

import com.example.weatherforecastapplication.api.ApiService
import com.example.weatherforecastapplication.api.Constants
import com.example.weatherforecastapplication.model.WeatherResponse
import javax.inject.Inject

interface WeatherRemoteSource {
    suspend fun getWeatherForecast(lat:Double,lon:Double):WeatherResponse

    class WeatherRemoteSourceImp @Inject constructor(private val apiService: ApiService):WeatherRemoteSource {
        override suspend fun getWeatherForecast(lat:Double,lon:Double): WeatherResponse {
            return apiService.fetWeatherForecast(lat,lon,Constants.API_KEY)
        }

    }
}