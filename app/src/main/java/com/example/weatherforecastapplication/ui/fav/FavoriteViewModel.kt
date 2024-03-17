package com.example.weatherforecastapplication.ui.fav

import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.base.BaseViewModel
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.ui.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(val repo: WeatherRepo):BaseViewModel<Navigator>() {
    lateinit var navigator: Navigator
    private val _favLocation= MutableStateFlow<ResultState<List<WeatherDataEntity>>>(ResultState.Loading)
    val favLocation:StateFlow<ResultState<List<WeatherDataEntity>>>
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

     fun getFavLocations(){
        _favLocation.value = ResultState.Loading

        viewModelScope.launch(Dispatchers.IO) {
                repo.getAllFavLocations().catch {
                    _favLocation.value = ResultState.Error(it.localizedMessage ?: "Unknown error")

                }.collect { favProducts ->
                    _favLocation.value = ResultState.Success(favProducts)
                }

        }
    }

    fun navigateToMapSelectionFragment(){
        navigator.navigateToMapSelectionFragment()
    }

}