package com.example.weatherforecastapplication.ui.favdetails

import com.example.weatherforecastapplication.base.BaseViewModel
import com.example.weatherforecastapplication.data.model.WeatherDataEntity

class FavDetailsViewModel: BaseViewModel<Navigator>() {
    var weatherDataEntity: WeatherDataEntity?=null
}