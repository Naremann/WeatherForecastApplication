package com.example.weatherforecastapplication.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.base.BaseViewModel
import com.example.weatherforecastapplication.model.Current
import com.example.weatherforecastapplication.model.HourlyItem
import com.example.weatherforecastapplication.model.WeatherItem
import com.example.weatherforecastapplication.model.WeatherResponse
import com.example.weatherforecastapplication.repo.WeatherRepo
import com.example.weatherforecastapplication.model.convertKelvinToCelsius
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@ViewModelScoped
class HomeViewModel (val weatherRepo: WeatherRepo) : BaseViewModel<Navigator>() {
    var currentWeather: Current? = null
    var weatherResponse:WeatherResponse?=null
     var hourlyItem: List<HourlyItem?>?=null
     var dailyWeather: List<WeatherItem?>?=null
    var currentDate: String? = null
    var currentTime: String? = null
    var tempRang:String?=null
    var maxTemp :String?=null
    var minTemp :String?=null
    var temp:String?=null
    var iconCode:String?=null
    val hourlyAdapter=HourlyWeatherAdapter()
    val dailyAdapter=DailyWeatherAdapter()



    fun getWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val result = weatherRepo.getWeatherForecast(latitude, longitude)
                weatherResponse=result
                currentWeather=result.current

                result.current?.let {
                    currentWeather = it
                    hourlyAdapter.submitList(result.hourly)
                    dailyAdapter.submitList(result.daily)
                   /* maxTemp= currentWeather?.main?.tempMax?.minus(273.15)?.toInt().toString()
                    minTemp= currentWeather?.main?.tempMin?.minus(273.15)?.toInt().toString()*/
//                    temp= (currentWeather?.temp?.minus(273.15)?.toInt()).toString()
                    temp= currentWeather?.temp?.let { it1 -> convertKelvinToCelsius(temp = it1) }
                    iconCode= currentWeather?.weather?.get(0)?.icon
                    Log.e("HomeViewModel", "max: $result.hourly")

                    //tempRang= "${maxTemp}°/${minTemp}°"

                }

                val calendar = Calendar.getInstance()
                currentDate = formatDate(calendar.time, "EEE, dd MMM")
                currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)

                Log.d("HomeViewModel", "getWeatherData: $currentWeather")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "getWeatherData: ${e.localizedMessage}")
            }
        }
    }

    private fun formatDate(date: Date, pattern: String): String {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormat.format(date)
    }
}

