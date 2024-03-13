package com.example.weatherforecastapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherforecastapplication.data.model.WeatherAlertEntity
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
@Database(entities = [WeatherDataEntity::class,WeatherAlertEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
    abstract fun getWeatherAlertDao():WeatherAlertDao

    companion object {
        const val DATABASE_NAME = "MyDatabase"

        /*@Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }*/
    }
}

