package com.example.weatherforecastapplication.ui.favdetails

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.base.BaseFragment
import com.example.weatherforecastapplication.databinding.FragmentFavDetailsBinding
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.ui.home.DailyWeatherAdapter
import com.example.weatherforecastapplication.ui.home.HourlyWeatherAdapter

class FavDetailsFragment : BaseFragment<FragmentFavDetailsBinding, FavDetailsViewModel>() {
    private val hourlyWeatherAdapter=HourlyWeatherAdapter()
    private val dailyWeatherAdapter=DailyWeatherAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel=viewModel
        viewDataBinding.lifecycleOwner=this
        val weatherData=FavDetailsFragmentArgs.fromBundle(requireArguments()).weatherData
        initHourlyRecyclerView(weatherData)
        initDailyRecyclerView(weatherData)
        viewModel.weatherDataEntity=weatherData
    }

    private fun initDailyRecyclerView(weatherData: WeatherDataEntity?) {
        dailyWeatherAdapter.submitList(weatherData?.dailyWeather)
        viewDataBinding.dailyRecyclerView.adapter=dailyWeatherAdapter
    }

    private fun initHourlyRecyclerView(weatherData: WeatherDataEntity?) {
        hourlyWeatherAdapter.submitList(weatherData?.hourlyWeather)
        viewDataBinding.recyclerView.adapter=hourlyWeatherAdapter
    }

    override fun getFragmentView(): View? {
        return viewDataBinding.root
    }

    override fun initViewModel(): FavDetailsViewModel {
        return ViewModelProvider(this)[FavDetailsViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_fav_details
    }


}