package com.example.weatherforecastapplication.ui.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weatherforecastapplication.model.DailyItem
import com.example.weatherforecastapplication.model.convertKelvinToCelsius
import com.example.weatherforecastapplication.model.formatTimestamp

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

}