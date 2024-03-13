package com.example.weatherforecastapplication.ui.map

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.base.BaseViewModel
import com.example.weatherforecastapplication.data.db.PreferenceManager
import com.example.weatherforecastapplication.data.model.getAddress
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.ui.ResultState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapSelectionViewModel(val repo: WeatherRepo):BaseViewModel<Navigator>() {
    lateinit var navigator: com.example.weatherforecastapplication.ui.fav.Navigator
    private val _favLocation= MutableStateFlow<ResultState>(ResultState.Loading)
    val favLocation: StateFlow<ResultState>
        get() =_favLocation

    fun saveLocationToFav(latLng: LatLng,context: Context,preferenceManager: PreferenceManager) {
        _favLocation.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weatherData=repo.getWeatherForecast(latLng.latitude,latLng.longitude,preferenceManager.checkLanguage())
                weatherData.city= getAddress(latLng.latitude,latLng.latitude,context)
                repo.insertLocationToFav(
                   weatherData
                    )
                _favLocation.value=ResultState.SuccessMsg("saved Successfully")
            } catch (ex: Exception) {
                _favLocation.value = ResultState.Error(ex.localizedMessage ?: "Unknown Error")
            }
        }
    }
}