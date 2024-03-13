package com.example.weatherforecastapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherforecastapplication.data.model.WeatherAlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherAlertDao {
    @Query("SELECT * FROM weather_alerts")
    fun getAllWeatherAlerts(): Flow<List<WeatherAlertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeatherAlert(alert: WeatherAlertEntity)

    @Update
    suspend fun updateWeatherAlert(alert: WeatherAlertEntity)
}
