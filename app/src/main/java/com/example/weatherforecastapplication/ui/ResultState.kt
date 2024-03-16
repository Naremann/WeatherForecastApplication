package com.example.weatherforecastapplication.ui

import com.example.weatherforecastapplication.data.model.WeatherDataEntity

sealed class ResultState<out T> {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val message: String?) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
   /* class Success(val weatherDataEntities:List<WeatherDataEntity>): ResultState()
    data class SuccessMsg(val msg: String) : ResultState()
    class Error(val error:String):ResultState()*/
   // data object Loading:ResultState()
   data class SuccessMsg(val msg: String) : ResultState<Nothing>()
}