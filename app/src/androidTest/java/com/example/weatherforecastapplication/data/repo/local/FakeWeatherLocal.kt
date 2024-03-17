package com.example.weatherforecastapplication.data.repo.local

import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeatherLocal: WeatherLocalSource {
    private val favLocations= mutableListOf<WeatherDataEntity>()
    override suspend fun insertLocationToFav(weatherDataEntity: WeatherDataEntity) {
        favLocations.add(weatherDataEntity)
    }

    override fun getAllFavLocations(): Flow<List<WeatherDataEntity>> {
        return flow {
            emit(favLocations)
        }
    }

    override suspend fun deleteLocationFromFav(weatherDataEntity: WeatherDataEntity) {
        favLocations.remove(weatherDataEntity)
    }

    override suspend fun saveLatestLocationData(weatherDataEntity: WeatherDataEntity) {
        weatherDataEntity.isLastLocation=true
        favLocations.add(weatherDataEntity)
    }

    override fun getLatestLocationData(): WeatherDataEntity {
        TODO("Not yet implemented")
    }
}