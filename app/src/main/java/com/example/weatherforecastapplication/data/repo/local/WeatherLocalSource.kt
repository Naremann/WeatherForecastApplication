package com.example.weatherforecastapplication.data.repo.local

import com.example.weatherforecastapplication.data.db.LocationDao
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WeatherLocalSource {
    suspend fun insertLocationToFav(weatherDataEntity: WeatherDataEntity)
    fun getAllFavLocations(): Flow<List<WeatherDataEntity>>
    suspend fun deleteLocationFromFav(weatherDataEntity: WeatherDataEntity)

    class WeatherLocalSourceImp @Inject constructor(private val locationDao: LocationDao):
        WeatherLocalSource {
        override suspend fun insertLocationToFav(weatherDataEntity: WeatherDataEntity) {
                locationDao.insertLocationToFav(weatherDataEntity)

        }

        override fun getAllFavLocations(): Flow<List<WeatherDataEntity>> {
            return locationDao.getAllFavLocations()
        }

        override suspend fun deleteLocationFromFav(weatherDataEntity: WeatherDataEntity) {
            locationDao.deleteLocationFromFav(weatherDataEntity)
        }

    }
}