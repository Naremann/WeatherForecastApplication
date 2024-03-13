package com.example.weatherforecastapplication.data.repo

import com.example.weatherforecastapplication.data.model.WeatherAlertEntity
import com.example.weatherforecastapplication.data.repo.local.WeatherAlertLocalSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WeatherAlertRepo {
    fun getAllWeatherAlerts(): Flow<List<WeatherAlertEntity>>
    suspend fun addWeatherAlert(alert: WeatherAlertEntity)
    suspend fun updateWeatherAlert(alert: WeatherAlertEntity)
}
class WeatherAlertRepoImp @Inject constructor(private val weatherAlertLocalSource: WeatherAlertLocalSource):WeatherAlertRepo{
    override fun getAllWeatherAlerts(): Flow<List<WeatherAlertEntity>> {
        return weatherAlertLocalSource.getAllWeatherAlerts()
    }

    override suspend fun addWeatherAlert(alert: WeatherAlertEntity) {
        return weatherAlertLocalSource.addWeatherAlert(alert)
    }

    override suspend fun updateWeatherAlert(alert: WeatherAlertEntity) {
        return weatherAlertLocalSource.updateWeatherAlert(alert)
    }


}