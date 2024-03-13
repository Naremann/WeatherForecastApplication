package com.example.weatherforecastapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationToFav(weatherDataEntity: WeatherDataEntity)

    @Query("select * from weather_data")
    fun getAllFavLocations(): Flow<List<WeatherDataEntity>>

    @Delete
    suspend fun deleteLocationFromFav(weatherDataEntity: WeatherDataEntity)
}