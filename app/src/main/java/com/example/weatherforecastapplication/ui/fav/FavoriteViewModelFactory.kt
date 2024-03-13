package com.example.weatherforecastapplication.ui.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import java.lang.IllegalArgumentException

class FavoriteViewModelFactory(private val repo: WeatherRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}