package com.example.weatherforecastapplication.data.repo.remote

import com.example.weatherforecastapplication.data.api.ApiService
import com.example.weatherforecastapplication.data.api.Constants
import com.example.weatherforecastapplication.data.model.WeatherResponse
import javax.inject.Inject

interface WeatherRemoteSource {
    suspend fun getWeatherForecast(lat:Double,lon:Double,lang:String): WeatherResponse

    class WeatherRemoteSourceImp @Inject constructor(private val apiService: ApiService):
        WeatherRemoteSource {
        override suspend fun getWeatherForecast(lat:Double,lon:Double,lang:String): WeatherResponse {
            return apiService.fetWeatherForecast(lat,lon, Constants.API_KEY,lang)
        }

    }
}