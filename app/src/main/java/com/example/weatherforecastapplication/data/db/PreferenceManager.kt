package com.example.weatherforecastapplication.data.db

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherforecastapplication.data.model.convertKelvinToCelsius
import com.example.weatherforecastapplication.data.model.convertKelvinToFahrenheit
import com.example.weatherforecastapplication.data.model.convertWindSpeedInMStoMPHunit

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

    fun checkLanguage(): String {
        return if (getLanguage() == "English") {
            "en"
        } else {
            "ar"
        }
    }

     fun setTempBasedOnUnit(temp:Double):String{
        return if(getTempUnit()=="Celsius")
            convertKelvinToCelsius(temp)
        else if(getTempUnit()=="Fahrenheit)")
            convertKelvinToFahrenheit(temp)
        else
            temp.toString()

    }

     fun setWindSpeedBasedOnUnit(windSpeed:Double):String{
        return if(getWindSpeed()=="Meter/Sec")
            convertWindSpeedInMStoMPHunit(windSpeed)
        else
            windSpeed.toString()
    }
}
