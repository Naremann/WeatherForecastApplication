package com.example.weatherforecastapplication.ui.alerts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.data.model.WeatherAlertEntity
import com.example.weatherforecastapplication.databinding.ListItemWeatherAlertBinding

class WeatherAlertAdapter :
    ListAdapter<WeatherAlertEntity, WeatherAlertAdapter.AlertViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val binding = ListItemWeatherAlertBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val alert = getItem(position)
        holder.bind(alert)
    }

    inner class AlertViewHolder(private val binding: ListItemWeatherAlertBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(alert: WeatherAlertEntity) {
            binding.tvDuration.text = "Duration: ${alert.duration}"
            binding.tvAlarmType.text = "Alarm Type: ${alert.alarmType}"
            binding.switchActive.isChecked = alert.isActive

            binding.switchActive.setOnCheckedChangeListener { _, isChecked ->
                val updatedAlert = alert.copy(isActive = isChecked)
                onItemClickListener?.invoke(updatedAlert)
            }
        }
    }

    private var onItemClickListener: ((WeatherAlertEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (WeatherAlertEntity) -> Unit) {
        onItemClickListener = listener
    }

    object DiffCallback : DiffUtil.ItemCallback<WeatherAlertEntity>() {
        override fun areItemsTheSame(oldItem: WeatherAlertEntity, newItem: WeatherAlertEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherAlertEntity, newItem: WeatherAlertEntity): Boolean {
            return oldItem == newItem
        }
    }
}
