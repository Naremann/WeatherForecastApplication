package com.example.weatherforecastapplication.ui.setting

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.base.BaseViewModel
import com.example.weatherforecastapplication.db.PreferenceManager
import com.example.weatherforecastapplication.ui.setting.Navigator

class SettingViewModel(private val sharedManager: PreferenceManager): BaseViewModel<Navigator>() {
    lateinit var navigator:Navigator
   // val context=context
    private val _selectedTemperatureUnit = MutableLiveData<String>()
    val selectedTemperatureUnit: LiveData<String>
        get() = _selectedTemperatureUnit

    private val _selectedWindSpeedUnit = MutableLiveData<String>()
    val selectedWindSpeedUnit: LiveData<String>
        get() = _selectedWindSpeedUnit

    private val _selectedLanguage = MutableLiveData<String>()
    val selectedLanguage: LiveData<String>
        get() = _selectedLanguage

    private val _locationMode = MutableLiveData<String>()
    val locationMode: LiveData<String>
        get() = _locationMode

    init {
        _locationMode.value = sharedManager.getLocationMode()
        _selectedTemperatureUnit.value = sharedManager.getTempUnit()
        _selectedWindSpeedUnit.value = sharedManager.getWindSpeed()
        _selectedLanguage.value = sharedManager.getLanguage()

    }

    fun setTemperatureUnit(unit: String) {
        _selectedTemperatureUnit.value = unit
        sharedManager.saveTempUnit(unit)
    }

    fun setWindSpeedUnit(unit: String) {
        _selectedWindSpeedUnit.value = unit
        sharedManager.saveWindSpeed(unit)
    }

    fun setLanguage(language: String) {
        _selectedLanguage.value = language
        sharedManager.saveLanguage(language)

    }

    fun setLocationMode(mode: String) {
        _locationMode.value = mode
         sharedManager.saveLocationMode(mode)
       // PreferenceManager.saveLocationMode(mode,context)

    }
    fun navigateToMapSelectionFragment(){
        navigator.navigateToMapSelectionFragment()
    }
    fun navigateToHomeFragment(){
        navigator.navigateToHomeFragment()
    }
}