package com.example.weatherforecastapplication.ui.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapplication.data.db.WeatherAlertDao
import com.example.weatherforecastapplication.data.repo.WeatherAlertRepo
import com.example.weatherforecastapplication.data.repo.WeatherRepo

class AlertsViewModelFactory(private val weatherAlertRepo: WeatherAlertRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeatherAlertsViewModel::class.java)){
            return WeatherAlertsViewModel(weatherAlertRepo) as T
        }
         throw IllegalArgumentException("Unknown ViewModel class")
    }
}