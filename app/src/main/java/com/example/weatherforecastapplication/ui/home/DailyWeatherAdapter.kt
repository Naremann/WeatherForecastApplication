package com.example.weatherforecastapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.databinding.ItemWeatherDailyBinding
import com.example.weatherforecastapplication.data.model.DailyItem

class DailyWeatherAdapter : ListAdapter<DailyItem, DailyWeatherAdapter.ViewHolder>(WeatherDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherDailyBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherItem = getItem(position)
        holder.bind(weatherItem)
    }

    inner class ViewHolder(private val binding: ItemWeatherDailyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DailyItem) {
            binding.dailyItem = item
            binding.executePendingBindings()
        }
    }
    private class WeatherDiffCallback : DiffUtil.ItemCallback<DailyItem>() {
        override fun areItemsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem == newItem
        }
    }
}
