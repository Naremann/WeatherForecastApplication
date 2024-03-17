package com.example.weatherforecastapplication.data.repo

import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.ui.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class FakeWeatherRepo: WeatherRepo {
    private var shouldError: Boolean = false
    private var shouldGetError: Boolean = false

    fun simulateGetError(shouldGetError: Boolean) {
        this.shouldGetError = shouldGetError
    }


    fun simulateError(shouldError: Boolean) {
        this.shouldError = shouldError
    }

    private val fakeWeatherList = mutableListOf<WeatherDataEntity>()

    override suspend fun insertLocationToFav(weatherDataEntity: WeatherDataEntity) {
        fakeWeatherList.add(weatherDataEntity)
    }

    override fun getAllFavLocations(): Flow<List<WeatherDataEntity>> {
        return flow {
            if (shouldGetError) {
                throw Exception("Fetch error")
            } else {
                emit(fakeWeatherList)
            }
        }
    }

    override suspend fun deleteLocationFromFav(weatherDataEntity: WeatherDataEntity) {
        if(shouldError){
            throw Exception("Deletion Failed!")
        }
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

    override suspend fun saveLatestLocationData(weatherDataEntity: WeatherDataEntity) {
        weatherDataEntity.isLastLocation=true
        fakeWeatherList.add(weatherDataEntity)
    }
}