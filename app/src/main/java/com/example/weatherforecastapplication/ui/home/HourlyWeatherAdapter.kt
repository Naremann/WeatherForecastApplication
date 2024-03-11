package com.example.weatherforecastapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.databinding.ItemWeatherHourlyBinding
import com.example.weatherforecastapplication.model.HourlyItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HourlyWeatherAdapter : ListAdapter<HourlyItem, HourlyWeatherAdapter.ViewHolder>(WeatherDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherHourlyBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherItem = getItem(position)
        holder.bind(weatherItem)
    }

    inner class ViewHolder(private val binding: ItemWeatherHourlyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HourlyItem) {
            binding.hourlyItem = item
            binding.executePendingBindings()
        }
    }
    private class WeatherDiffCallback : DiffUtil.ItemCallback<HourlyItem>() {
        override fun areItemsTheSame(oldItem: HourlyItem, newItem: HourlyItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: HourlyItem, newItem: HourlyItem): Boolean {
            return oldItem == newItem
        }
    }
}
