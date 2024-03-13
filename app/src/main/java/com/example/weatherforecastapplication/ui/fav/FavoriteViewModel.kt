package com.example.weatherforecastapplication.ui.fav

import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.base.BaseViewModel
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.ui.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(val repo: WeatherRepo):BaseViewModel<Navigator>() {
    lateinit var navigator: Navigator
    private val _favLocation= MutableStateFlow<ResultState>(ResultState.Loading)
    val favLocation:StateFlow<ResultState>
        get() =_favLocation

    init {
        getFavLocations()
    }

     fun deleteFavLocation(weatherDataEntity: WeatherDataEntity){
        viewModelScope.launch(Dispatchers.IO) {
            _favLocation.value=ResultState.Loading
            try {
                _favLocation.value=ResultState.SuccessMsg("deleted successfully")
                repo.deleteLocationFromFav(weatherDataEntity)
            } catch (ex: Exception) {
                _favLocation.value = ResultState.Error(ex.localizedMessage ?: "Unknown error")
            }
        }
    }

    private fun getFavLocations(){
        viewModelScope.launch(Dispatchers.IO) {
            _favLocation.value = ResultState.Loading
            try {
                repo.getAllFavLocations().collect { favProducts ->
                    _favLocation.value = ResultState.Success(favProducts)
                }
            } catch (ex: Exception) {
                _favLocation.value = ResultState.Error(ex.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun navigateToMapSelectionFragment(){
        navigator.navigateToMapSelectionFragment()
    }

}