package com.example.weatherforecastapplication.ui.map

import com.example.weatherforecastapplication.ui.ResultState

sealed class Result {
    data class Success(val msg: String) : Result()
    class Error(val error:String): Result()
    data object Loading: Result()
}