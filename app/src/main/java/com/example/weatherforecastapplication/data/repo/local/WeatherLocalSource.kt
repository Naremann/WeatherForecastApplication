package com.example.weatherforecastapplication.data.repo.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecastapplication.data.db.LocationDao
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WeatherLocalSource {
    suspend fun insertLocationToFav(weatherDataEntity: WeatherDataEntity)
    fun getAllFavLocations(): Flow<List<WeatherDataEntity>>
    suspend fun deleteLocationFromFav(weatherDataEntity: WeatherDataEntity)

    suspend fun saveLatestLocationData(weatherDataEntity: WeatherDataEntity)

    fun getLatestLocationData(): WeatherDataEntity

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

        override suspend fun saveLatestLocationData(weatherDataEntity: WeatherDataEntity) {
            weatherDataEntity.isLastLocation=true
            return locationDao.saveLatestLocationData(weatherDataEntity)
        }

        override fun getLatestLocationData(): WeatherDataEntity {
            return locationDao.getLatestLocationData()
        }

    }
}