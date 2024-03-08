package com.example.weatherforecastapplication

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.weatherforecastapplication.model.HourlyItem
import com.example.weatherforecastapplication.model.weatherIconResourceId
import com.example.weatherforecastapplication.ui.home.HourlyWeatherAdapter

@BindingAdapter("drawableImg")
fun setDrawableImage(imageView: ImageView, iconCode: String?) {
    iconCode?.let {
        val drawableId = weatherIconResourceId(iconCode)
        imageView.setImageResource(drawableId)
    }
}

