package com.example.weatherforecastapplication.db

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager (context: Context){
   companion object{
       private const val PREF_NAME = "MyPrefs"
       private const val LOCATION_MODE="location_mode"
       private const val LANGUAGE="language"
       private const val TEMP_UNIT="tempUnit"
       private const val  WIND_SPEED="windSpeed"

   }

    private val sharedPreferences=context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )


    fun saveLanguage(lang:String){
        sharedPreferences.edit().putString(LANGUAGE,lang).apply()
    }
    fun getLanguage():String{
        return sharedPreferences.getString(LANGUAGE,"English") ?: "English"
    }

    fun saveTempUnit(lang:String){
        sharedPreferences.edit().putString(TEMP_UNIT,lang).apply()
    }
    fun getTempUnit():String{
        return sharedPreferences.getString(TEMP_UNIT,"Celsius") ?: "Celsius"
    }

    fun saveWindSpeed(lang:String){
        sharedPreferences.edit().putString(WIND_SPEED,lang).apply()
    }
    fun getWindSpeed():String{
        return sharedPreferences.getString(WIND_SPEED,"Meter/Sec") ?: "Meter/Sec"
    }

    fun saveLocationMode(mode: String) {
        sharedPreferences.edit().putString(LOCATION_MODE, mode)?.apply()
    }

    fun getLocationMode(): String {
        return sharedPreferences.getString(LOCATION_MODE, "GPS") ?: "GPS"
    }
}

/*object PreferenceManager {
    private const val PREF_NAME = "MyPrefs"

    private fun getSharedPreferences(context: Context): SharedPreferences? {
        return context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }
    fun saveLocationMode(mode: String,context: Context) {
        getSharedPreferences(context)?.edit()?.putString("location_mode", mode)?.apply()
    }

    fun getLocationMode(context: Context): String {
        return getSharedPreferences(context)?.getString("location_mode", "GPS") ?: "GPS"
    }
}*/
