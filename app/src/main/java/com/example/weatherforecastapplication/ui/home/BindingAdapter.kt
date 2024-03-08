package com.example.weatherforecastapplication.ui.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weatherforecastapplication.model.DailyItem
import com.example.weatherforecastapplication.model.convertTimestampToTime
import com.example.weatherforecastapplication.model.convertKelvinToCelsius
import com.example.weatherforecastapplication.model.convertTimestampToDate
import com.example.weatherforecastapplication.model.convertTimestampToDay

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("timeFromTimestamp")
    fun setTimeFromTimestamp(textView: TextView, timestamp: Int?) {
        timestamp?.let {
            textView.text = convertTimestampToTime(it.toLong())
        }
    }

    @JvmStatic
    @BindingAdapter("dayFromTimestamp")
    fun setDayFromTimestamp(textView: TextView,timestamp: Int?){
        textView.text= timestamp?.toLong()?.let { convertTimestampToDay(it) }
    }

    @JvmStatic
    @BindingAdapter("dateFromTimeStamp")
    fun setDateFromTimeStamp(textView: TextView,timestamp: Int?){
        textView.text= timestamp?.toLong()?.let { convertTimestampToDate(it) }
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

}