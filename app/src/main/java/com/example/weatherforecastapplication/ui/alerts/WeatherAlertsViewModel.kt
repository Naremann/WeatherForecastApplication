package com.example.weatherforecastapplication.ui.alerts

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.base.BaseViewModel
import com.example.weatherforecastapplication.data.model.WeatherAlertEntity
import com.example.weatherforecastapplication.data.repo.WeatherAlertRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class WeatherAlertsViewModel(private val weatherAlertRepo: WeatherAlertRepo):BaseViewModel<Navigator>() {
    private val _weatherAlertsViewState = MutableStateFlow<WeatherAlertsViewState>(WeatherAlertsViewState.Loading)
    val weatherAlertsViewState: StateFlow<WeatherAlertsViewState> = _weatherAlertsViewState
    init {
        fetchWeatherAlerts()
    }

     fun fetchWeatherAlerts() {
        viewModelScope.launch {
            _weatherAlertsViewState.value = WeatherAlertsViewState.Loading
            try {
                val weatherAlerts = weatherAlertRepo.getAllWeatherAlerts()
                weatherAlerts.collect{weatherAlertList->
                    Log.e("TAG", "fetchWeatherAlerts: "+ weatherAlertList[0].alarmType )
                    _weatherAlertsViewState.value = WeatherAlertsViewState.Success(weatherAlertList)

                }
            } catch (e: Exception) {
                _weatherAlertsViewState.value = WeatherAlertsViewState.Error("Failed to fetch weather alerts")
            }
        }
    }

    fun addWeatherAlert(weatherAlert: WeatherAlertEntity,context:Context) {
         val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        viewModelScope.launch {
            weatherAlertRepo.addWeatherAlert(weatherAlert)

            val alarmIntent = Intent(
                context,
                AlarmReceiver::class.java
            )
            alarmIntent.putExtra(
                AlarmReceiver.ALERT_TYPE_EXTRA,
                weatherAlert.alarmType
            )
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                weatherAlert.id.toInt(),
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, weatherAlert.hour)
            calendar.set(Calendar.MINUTE, weatherAlert.minute)
            calendar.set(Calendar.SECOND, 0)
            val alarmTime = calendar.timeInMillis

            Log.d("WeatherAlertsViewModel", "Alarm time: $alarmTime")

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        alarmTime,
                        pendingIntent
                    )
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        alarmTime,
                        pendingIntent
                    )
                } else {
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        alarmTime,
                        pendingIntent
                    )
                }
            } catch (e: SecurityException) {
                Log.e("WeatherAlertsViewModel", "SecurityException: ${e.message}")
                // Handle the SecurityException here
            }
    }
    }

    fun updateWeatherAlert(alert: WeatherAlertEntity) {
        viewModelScope.launch {
            weatherAlertRepo.deleteWeatherAlert(alert)
            fetchWeatherAlerts()
        }
    }
}