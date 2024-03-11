/*
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
                   */
/* maxTemp= currentWeather?.main?.tempMax?.minus(273.15)?.toInt().toString()
                    minTemp= currentWeather?.main?.tempMin?.minus(273.15)?.toInt().toString()*//*

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

*/

package com.example.weatherforecastapplication.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.base.BaseViewModel
import com.example.weatherforecastapplication.db.PreferenceManager
import com.example.weatherforecastapplication.model.Current
import com.example.weatherforecastapplication.model.WeatherResponse
import com.example.weatherforecastapplication.repo.WeatherRepo
import com.example.weatherforecastapplication.model.convertKelvinToCelsius
import com.example.weatherforecastapplication.model.convertKelvinToFahrenheit
import com.example.weatherforecastapplication.model.convertWindSpeedInMStoMPHunit
import com.example.weatherforecastapplication.model.formatDate
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@ViewModelScoped
class HomeViewModel(private val weatherRepo: WeatherRepo,val preferenceManager: PreferenceManager) : BaseViewModel<Navigator>() {
    var currentWeather: Current? = null
    private var weatherResponse: WeatherResponse? = null
    var currentDate: String? = null
    var currentTime: String? = null
    var tempRange: String? = null
    var maxTemp: String? = null
    var minTemp: String? = null
    var temp: String? = null
    var windSpeed:String?=null
    var iconCode: String? = null
    val hourlyAdapter = HourlyWeatherAdapter()
    val dailyAdapter = DailyWeatherAdapter()

    fun getWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val language:String=checkLanguage()
                val result = weatherRepo.getWeatherForecast(latitude, longitude,language)
                weatherResponse = result
                currentWeather = result.current
                result.current?.let {
                    currentWeather = it
                    hourlyAdapter.submitList(result.hourly)
                    dailyAdapter.submitList(result.daily)
                    temp= currentWeather?.temp?.let { it1 -> setTempBasedOnUnit(it1) }
                    windSpeed= currentWeather?.windSpeed?.let { it1 -> setWindSpeedBasedOnUnit(it1) }
                    iconCode = currentWeather?.weather?.get(0)?.icon
                    Log.e("HomeViewModel", "max: ${result.hourly}")
                }

                setCurrentDateAndTime()

                Log.d("HomeViewModel", "getWeatherData: $currentWeather")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "getWeatherData: ${e.localizedMessage}")
            }
        }
    }

    private fun setCurrentDateAndTime() {
        val calendar = Calendar.getInstance()
        currentDate = formatDate(calendar.time, "EEE, dd MMM")
        currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
    }

    private fun checkLanguage(): String {
        return if(preferenceManager.getLanguage()=="English")
            "en"
        else
            "ar"

    }

    private fun setTempBasedOnUnit(temp:Double):String{
        return if(preferenceManager.getTempUnit()=="Celsius")
            convertKelvinToCelsius(temp)
        else if(preferenceManager.getTempUnit()=="Fahrenheit)")
            convertKelvinToFahrenheit(temp)
        else
            temp.toString()

    }

    private fun setWindSpeedBasedOnUnit(windSpeed:Double):String{
        return if(preferenceManager.getWindSpeed()=="Meter/Sec")
            convertWindSpeedInMStoMPHunit(windSpeed)
        else
            windSpeed.toString()
    }


}

