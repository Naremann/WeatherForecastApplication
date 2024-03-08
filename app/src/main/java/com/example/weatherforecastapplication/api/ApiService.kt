package com.example.weatherforecastapplication.api

import com.example.weatherforecastapplication.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


        @GET("/data/3.0/onecall")
        suspend fun fetWeatherForecast(
            @Query("lat") latitude: Double,
            @Query("lon") longitude: Double,
            @Query("appid") apiKey: String
        ): WeatherResponse
}