package com.example.weatherforecastapplication.data.model

import androidx.databinding.adapters.Converters
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "weather_data")
@TypeConverters(Converter::class)
data class WeatherDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var city: String?,
    var temp: String?,
    val description:String?,
    var windSpeed: String?,
    val humidity: String?,
    val feelsLike: String?,
    val pressure: String?,
    val clouds: String?,
    val iconCode: String?,
    val hourlyWeather: List<HourlyItem?>?,
    val dailyWeather: List<DailyItem?>?,
    var isLastLocation:Boolean=false
):Serializable
