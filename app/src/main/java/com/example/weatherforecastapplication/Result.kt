package com.example.weatherforecastapplication

sealed class Result {
    data class Success(val msg: String) : Result()
    class Error(val error:String): Result()
    data object Loading: Result()
}