package com.example.weatherforecastapplication.data.repo.local

import com.example.weatherforecastapplication.data.db.WeatherAlertDao
import com.example.weatherforecastapplication.data.model.WeatherAlertEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WeatherAlertLocalSource {
    fun getAllWeatherAlerts(): Flow<List<WeatherAlertEntity>>
    suspend fun addWeatherAlert(alert: WeatherAlertEntity)
    suspend fun updateWeatherAlert(alert: WeatherAlertEntity)
}
class WeatherAlertLocalSourceImp @Inject constructor(private val weatherAlertDao: WeatherAlertDao):WeatherAlertLocalSource{
    override fun getAllWeatherAlerts(): Flow<List<WeatherAlertEntity>> {
        return weatherAlertDao.getAllWeatherAlerts()
    }

    override suspend fun addWeatherAlert(alert: WeatherAlertEntity) {
        weatherAlertDao.addWeatherAlert(alert)
    }

    override suspend fun updateWeatherAlert(alert: WeatherAlertEntity) {
        weatherAlertDao.updateWeatherAlert(alert)
    }
}