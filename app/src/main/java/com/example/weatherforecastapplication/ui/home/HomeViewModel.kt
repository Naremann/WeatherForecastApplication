package com.example.weatherforecastapplication.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.base.BaseViewModel
import com.example.weatherforecastapplication.data.db.PreferenceManager
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.model.formatDate
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.ui.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo,
    private val preferenceManager: PreferenceManager,
) : BaseViewModel<Navigator>() {
    var currentDate: String? = null
    var currentTime: String? = null
    var weatherData: WeatherDataEntity? = null
    private val _weatherDataStateFlow = MutableStateFlow<ResultState<WeatherDataEntity>>(ResultState.Loading)
    val weatherDataStateFlow: StateFlow<ResultState<WeatherDataEntity>>
        get() = _weatherDataStateFlow


    init {
        setCurrentDateAndTime()
    }

    fun getWeatherData(latitude: Double, longitude: Double) {
       _weatherDataStateFlow.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val language: String = preferenceManager.checkLanguage()
                val weatherDataEntity =
                    weatherRepo.getWeatherForecast(latitude, longitude, language)
                weatherData = weatherDataEntity

                _weatherDataStateFlow.value = ResultState.Success(weatherDataEntity)
                weatherDataEntity.temp = weatherDataEntity.temp?.let { temp ->
                    preferenceManager.setTempBasedOnUnit(temp.toDouble())
                }
                    weatherDataEntity.windSpeed = weatherData?.windSpeed?.toDouble()
                        ?.let { preferenceManager.setWindSpeedBasedOnUnit(it) }
//                    weatherData = weatherDataEntity
                    Log.e("TAG", "getWeatherData: " + weatherData?.city)
                saveLatestLocation(weatherDataEntity)

            } catch (e: Exception) {
                _weatherDataStateFlow.value =
                    ResultState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    private fun saveLatestLocation(weatherDataEntity: WeatherDataEntity) {
        viewModelScope.launch {
            try {
                weatherRepo.saveLatestLocationData(weatherDataEntity)

            }catch (ex:Exception){
                Log.e("TAG", "saveLatestLocation: "+ex.localizedMessage)
            }
        }
    }

    private fun setCurrentDateAndTime() {
        val calendar = Calendar.getInstance()
        currentDate = formatDate(calendar.time, "EEE, dd MMM")
        currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
    }


}


/*package com.example.weatherforecastapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.base.BaseViewModel
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.ui.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: WeatherRepo) : BaseViewModel<Navigator>() {

    private val _weatherDataStateFlow: MutableLiveData<ResultState<WeatherDataEntity?>> =
        MutableLiveData(ResultState.Loading)
    val weatherDataStateFlow: LiveData<ResultState<WeatherDataEntity?>>
        get() = _weatherDataStateFlow

    private val _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String>
        get() = _currentDate

    private val _currentTime = MutableLiveData<String>()
    val currentTime: LiveData<String>
        get() = _currentTime

    var weatherData: WeatherDataEntity? = null

    fun getWeatherData(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weatherDataStateFlow.value = ResultState.Loading
            try {
                weatherData = repository.getWeatherForecast(lat, lon, "en")
                _weatherDataStateFlow.value = ResultState.Success(weatherData)
            } catch (e: Exception) {
                _weatherDataStateFlow.value = ResultState.Error("Failed to get weather data.")
            }
        }
    }

}*/



