package com.example.weatherforecastapplication.data.repo

import androidx.lifecycle.MutableLiveData
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeatherRepo:WeatherRepo {

    private val fakeWeatherList = mutableListOf<WeatherDataEntity>()

    override suspend fun insertLocationToFav(weatherDataEntity: WeatherDataEntity) {
        fakeWeatherList.add(weatherDataEntity)
    }

    override fun getAllFavLocations(): Flow<List<WeatherDataEntity>> {
        return flow {
            emit(fakeWeatherList)
        }
    }

    override suspend fun deleteLocationFromFav(weatherDataEntity: WeatherDataEntity) {
        fakeWeatherList.remove(weatherDataEntity)
    }

    override suspend fun getWeatherForecast(
        lat: Double,
        lon: Double,
        lang: String
    ): WeatherDataEntity {
        return WeatherDataEntity(
            city = "Cairo City",
            temp = "18",
            windSpeed = "10",
            humidity = "60",
            feelsLike = "28",
            pressure = "1010",
            clouds = "20",
            iconCode = "01d",
            hourlyWeather = emptyList(),
            dailyWeather = emptyList(),
            description = "Clear Sky"
        )
    }
}