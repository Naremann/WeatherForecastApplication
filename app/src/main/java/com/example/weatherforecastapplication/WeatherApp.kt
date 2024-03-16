package com.example.weatherforecastapplication

import android.app.Application
import android.content.Context
import com.example.weatherforecastapplication.data.db.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WeatherApp:Application(){
    override fun onCreate() {
        super.onCreate()
        LanguageUtils.setDefaultLanguage(PreferenceManager(this), applicationContext)

    }
}