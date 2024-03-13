package com.example.weatherforecastapplication.ui

import com.example.weatherforecastapplication.data.model.WeatherDataEntity

sealed class ResultState {
    class Success(val locationModel:List<WeatherDataEntity>): ResultState()
    data class SuccessMsg(val msg: String) : ResultState()
    class Error(val error:String):ResultState()
    data object Loading:ResultState()
}