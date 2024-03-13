package com.example.weatherforecastapplication.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapplication.data.repo.WeatherRepo

class MapSelectionVMFactory (private val repo: WeatherRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapSelectionViewModel::class.java)) {
            return MapSelectionViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}