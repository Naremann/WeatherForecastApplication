package com.example.weatherforecastapplication.ui.alerts

import com.example.weatherforecastapplication.data.model.WeatherAlertEntity

sealed class WeatherAlertsViewState {
    data object Loading : WeatherAlertsViewState()
    data class Success(val weatherAlerts: List<WeatherAlertEntity>) : WeatherAlertsViewState()
    data class Error(val message: String) : WeatherAlertsViewState()
}
