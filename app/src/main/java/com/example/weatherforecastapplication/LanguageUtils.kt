package com.example.weatherforecastapplication

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import com.example.weatherforecastapplication.data.db.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale

object LanguageUtils {
     private fun setLocale(languageCode: String,context: Context) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
     fun setDefaultLanguage(preferenceManager: PreferenceManager,context: Context) {
         val languageCode = preferenceManager.checkLanguage()
         val defaultLanguageCode = if (languageCode == "en") "en" else "ar"
         setLocale(defaultLanguageCode, context)
    }
}