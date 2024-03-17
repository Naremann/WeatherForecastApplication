package com.example.weatherforecastapplication.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    private val gson= Gson()
    @TypeConverter
    fun fromHourlyWeatherList(hourlyItems:List<HourlyItem?>?):String?{
        return gson.toJson(hourlyItems)

    }

    @TypeConverter
    fun toHourlyWeatherList(hourlyItemsString: String):List<HourlyItem?>?{
        val type= object:TypeToken<List<HourlyItem?>?>() {}.type
        return gson.fromJson(hourlyItemsString,type)
    }

    @TypeConverter
    fun fromDailyWeatherList(dailyItems:List<DailyItem?>?):String?{
        return gson.toJson(dailyItems)
    }

    @TypeConverter
    fun toDailyWeatherList(dailyItemsString: String): List<DailyItem?>?{
        val type=object :TypeToken< List<DailyItem?>?>() {}.type
        return gson.fromJson(dailyItemsString,type)
    }


}