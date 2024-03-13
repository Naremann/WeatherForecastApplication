package com.example.weatherforecastapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_alerts")
data class WeatherAlertEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val hour: Int,
    val minute: Int,
    val duration: Long,
    val alarmType: String,
    val isActive: Boolean
)


