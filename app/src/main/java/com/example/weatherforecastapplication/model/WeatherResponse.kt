package com.example.weatherforecastapplication.model

import com.example.weatherforecastapplication.R
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.times

data class WeatherResponse(

	@field:SerializedName("current")
	val current: Current? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("timezone_offset")
	val timezoneOffset: Int? = null,

	@field:SerializedName("daily")
	val daily: List<DailyItem?>? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("hourly")
	val hourly: List<HourlyItem?>? = null,

	@field:SerializedName("minutely")
	val minutely: List<MinutelyItem?>? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)

data class HourlyItem(

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("visibility")
	val visibility: Int? = null,

	@field:SerializedName("uvi")
	val uvi: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Int? = null,

	@field:SerializedName("clouds")
	val clouds: Int? = null,

	@field:SerializedName("feels_like")
	val feelsLike: Any? = null,

	@field:SerializedName("wind_gust")
	val windGust: Any? = null,

	@field:SerializedName("dt")
	val dt: Int? = null,

	@field:SerializedName("pop")
	val pop: Double? = null,

	@field:SerializedName("wind_deg")
	val windDeg: Int? = null,

	@field:SerializedName("dew_point")
	val dewPoint: Any? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null
)

data class Current(

	@field:SerializedName("sunrise")
	val sunrise: Int? = null,

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("visibility")
	val visibility: Int? = null,

	@field:SerializedName("uvi")
	val uvi: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Int? = null,

	@field:SerializedName("clouds")
	val clouds: Int? = null,

	@field:SerializedName("feels_like")
	val feelsLike: Any? = null,

	@field:SerializedName("dt")
	val dt: Int? = null,

	@field:SerializedName("wind_deg")
	val windDeg: Int? = null,

	@field:SerializedName("dew_point")
	val dewPoint: Any? = null,

	@field:SerializedName("sunset")
	val sunset: Int? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null
)

data class WeatherItem(

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("main")
	val main: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Temp(

	@field:SerializedName("min")
	val min: Double? = null,

	@field:SerializedName("max")
	val max: Double? = null,

	@field:SerializedName("eve")
	val eve: Any? = null,

	@field:SerializedName("night")
	val night: Any? = null,

	@field:SerializedName("day")
	val day: Any? = null,

	@field:SerializedName("morn")
	val morn: Any? = null
)

data class DailyItem(

	@field:SerializedName("moonset")
	val moonset: Int? = null,

	@field:SerializedName("summary")
	val summary: String? = null,

	@field:SerializedName("rain")
	val rain: Any? = null,

	@field:SerializedName("sunrise")
	val sunrise: Int? = null,

	@field:SerializedName("temp")
	val temp: Temp? = null,

	@field:SerializedName("moon_phase")
	val moonPhase: Any? = null,

	@field:SerializedName("uvi")
	val uvi: Double? = null,

	@field:SerializedName("moonrise")
	val moonrise: Int? = null,

	@field:SerializedName("pressure")
	val pressure: Int? = null,

	@field:SerializedName("clouds")
	val clouds: Int? = null,

	@field:SerializedName("feels_like")
	val feelsLike: FeelsLike? = null,

	@field:SerializedName("wind_gust")
	val windGust: Any? = null,

	@field:SerializedName("dt")
	val dt: Int? = null,

	@field:SerializedName("pop")
	val pop: Double? = null,

	@field:SerializedName("wind_deg")
	val windDeg: Int? = null,

	@field:SerializedName("dew_point")
	val dewPoint: Any? = null,

	@field:SerializedName("sunset")
	val sunset: Int? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null
)

data class FeelsLike(

	@field:SerializedName("eve")
	val eve: Any? = null,

	@field:SerializedName("night")
	val night: Any? = null,

	@field:SerializedName("day")
	val day: Any? = null,

	@field:SerializedName("morn")
	val morn: Any? = null
)

data class MinutelyItem(

	@field:SerializedName("dt")
	val dt: Int? = null,

	@field:SerializedName("precipitation")
	val precipitation: Int? = null
)


fun convertKelvinToCelsius(temp:Double):String{
	return temp.minus(273.15).toInt().toString()
}

fun convertKelvinToFahrenheit(temp: Double):String{
	return ((temp.minus(273.15)).times(9/5)+32).toString()
}

fun convertWindSpeedInMStoMPHunit(windSpeed:Double):String{
	return windSpeed.times(3.6671).toString()
}

/*
fun convertTimestampToTime(timestamp: Long): String {
	val date = Date(timestamp * 1000)
	val sdf = SimpleDateFormat("hh:mm", Locale.getDefault())
	return sdf.format(date)
}
*/

/*fun convertTimestampToDay(timestamp: Long):String{
	return formatTimestamp(timestamp,"EEEE")

	*//*val date=Date(timestamp * 1000)
	val sdf=SimpleDateFormat("EEEE",Locale.getDefault())
	return sdf.format(date)*//*
}*/
/*fun convertTimestampToDate(timestamp: Long):String{
	return formatTimestamp(timestamp,"dd/MM")
	*//*val date= Date(timestamp * 1000)
	val sdf= SimpleDateFormat("dd/MM", Locale.getDefault())
	return sdf.format(date)*//*
}*/

fun formatTimestamp(timestamp: Long, pattern: String):String{
	val date=Date(timestamp * 1000)
	val sdf=SimpleDateFormat(pattern,Locale.getDefault())
	return sdf.format(date)
}
 fun formatDate(date: Date, pattern: String): String {
	val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
	return simpleDateFormat.format(date)
}
fun weatherIconResourceId(iconCode: String): Int {
	return when (iconCode) {
		"01d" -> R.drawable.day_clear
		"01n" -> R.drawable.night_clear
		"02d" -> R.drawable.day_partial_cloud
		"02n" -> R.drawable.night_partial_cloud
		"03d" -> R.drawable.cloudy
		"03n" -> R.drawable.night_partial_cloud
		"04d", "04n" -> R.drawable.overcast
		"09d", "09n" -> R.drawable.rain
		"10d" -> R.drawable.day_rain
		"10n" -> R.drawable.night_rain
		"11d", "11n" -> R.drawable.rain_thunder
		"13d", "13n" -> R.drawable.snow
		"50d", "50n" -> R.drawable.fog
		else -> R.drawable.day_clear
	}
}

