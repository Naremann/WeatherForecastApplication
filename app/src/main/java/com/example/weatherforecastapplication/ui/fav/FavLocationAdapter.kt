package com.example.weatherforecastapplication.ui.fav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.databinding.LocationFavItemBinding
import com.example.weatherforecastapplication.data.model.WeatherDataEntity

class FavLocationAdapter(): ListAdapter<WeatherDataEntity, FavLocationAdapter.ViewHolder>(FavLocationDiffCallback()) {
     var onItemClickListener:OnItemClickListener?=null
    class ViewHolder(private val itemBinding: LocationFavItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: WeatherDataEntity, onItemClickListener:OnItemClickListener){
            itemBinding.deleteIcon.setOnClickListener {
               onItemClickListener.onDeleteClick(item)
            }
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(item)
            }
            itemBinding.locationModel=item
            itemBinding.executePendingBindings()
        }
    }

    private class FavLocationDiffCallback: DiffUtil.ItemCallback<WeatherDataEntity>() {
        override fun areItemsTheSame(oldItem: WeatherDataEntity, newItem: WeatherDataEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherDataEntity, newItem: WeatherDataEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=LocationFavItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onItemClickListener?.let { holder.bind(getItem(position), it) }
    }

    interface OnItemClickListener{
        fun onDeleteClick(weatherDataEntity: WeatherDataEntity)
        fun onItemClick(weatherDataEntity: WeatherDataEntity)
    }
}