package com.example.weatherforecastapplication.ui.home

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.base.BaseViewModel
import com.example.weatherforecastapplication.data.db.PreferenceManager
import com.example.weatherforecastapplication.data.model.Current
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.model.WeatherResponse
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.data.model.formatDate
import com.example.weatherforecastapplication.data.model.getAddress
import com.example.weatherforecastapplication.ui.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeViewModel(private val weatherRepo: WeatherRepo, private val preferenceManager: PreferenceManager) : BaseViewModel<Navigator>() {
    var currentWeather: Current? = null
    var currentDate: String? = null
    var currentTime: String? = null
   // private var _weatherData=MutableLiveData<WeatherDataEntity>()
   private val _weatherDataStateFlow = MutableStateFlow<ResultState>(ResultState.Loading)
    val weatherDataStateFlow: StateFlow<ResultState>
        get() = _weatherDataStateFlow

    var weatherData: WeatherDataEntity? = null

    init {
        setCurrentDateAndTime()
    }

    fun getWeatherData(latitude: Double, longitude: Double, context: Context) {
        _weatherDataStateFlow.value = ResultState.Loading
        viewModelScope.launch {
            try {
                val language: String = preferenceManager.checkLanguage()
                val weatherDataEntity = weatherRepo.getWeatherForecast(latitude, longitude, language)

                // Modify the weatherData property
                weatherData = weatherDataEntity

                // Update the StateFlow with Success
                _weatherDataStateFlow.value = ResultState.Success(listOf(weatherDataEntity))

                // Update the temp based on unit
                weatherDataEntity.temp = weatherDataEntity.temp?.let { temp ->
                    preferenceManager.setTempBasedOnUnit(temp.toDouble())
                }

                // Update the city
                weatherDataEntity.city = getAddress(latitude, longitude, context)
            }  catch (e: Exception) {
                //updateResultState(ResultState.Error(e.localizedMessage ?: "Unknown error"))
                _weatherDataStateFlow.value=ResultState.Error(e.localizedMessage ?: "Unknown error")
               // Log.e("HomeViewModel", "getWeatherData: ${e.localizedMessage}")
            }
        }
    }

    private fun setCurrentDateAndTime() {
        val calendar = Calendar.getInstance()
        currentDate = formatDate(calendar.time, "EEE, dd MMM")
        currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
    }


}

