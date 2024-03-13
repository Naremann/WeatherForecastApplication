package com.example.weatherforecastapplication.ui.home

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.data.model.DailyItem
import com.example.weatherforecastapplication.data.model.convertKelvinToCelsius
import com.example.weatherforecastapplication.data.model.formatTimestamp

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("timeFromTimestamp")
    fun setTimeFromTimestamp(textView: TextView, timestamp: Int?) {
        timestamp?.let {
            textView.text = formatTimestamp(it.toLong(),"hh:mm")
        }
    }

    @JvmStatic
    @BindingAdapter("dayFromTimestamp")
    fun setDayFromTimestamp(textView: TextView,timestamp: Int?){
        textView.text= timestamp?.toLong()?.let { formatTimestamp(it,"EEEE") }
    }

    @JvmStatic
    @BindingAdapter("dateFromTimeStamp")
    fun setDateFromTimeStamp(textView: TextView,timestamp: Int?){
        textView.text= timestamp?.toLong()?.let { formatTimestamp(it,"dd/MM") }
    }


    @JvmStatic
    @BindingAdapter("rangeTempInCelsius")
    fun setTempRange(textView: TextView,dailyItem: DailyItem){
        textView.text= dailyItem.temp?.max?.let { convertKelvinToCelsius(it) } +"°/"+ dailyItem.temp?.min?.let {
            convertKelvinToCelsius(
                it
            )
        }+"°"
    }

    @JvmStatic
    @BindingAdapter("tempInCelsius")
    fun convertKelvinToCelsius(textView: TextView,temp:Double) {
        textView.text = convertKelvinToCelsius(temp)
    }


    @JvmStatic
    @BindingAdapter("weatherBackground")
    fun setWeatherBackground(imageView: ImageView, weatherCondition: String?) {
        val resourceId = when (weatherCondition) {
            "clear" -> R.drawable.sunny
            "cloudy" -> R.drawable.cloud
            "rainy" -> R.drawable.rainy
            else -> R.drawable.background_gradient
        }
        imageView.setImageResource(resourceId)
    }

}