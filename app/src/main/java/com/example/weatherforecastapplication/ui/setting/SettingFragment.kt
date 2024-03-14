package com.example.weatherforecastapplication.ui.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.base.BaseFragment
import com.example.weatherforecastapplication.databinding.FragmentSettingBinding
import com.example.weatherforecastapplication.data.db.PreferenceManager
import com.example.weatherforecastapplication.ui.fav.FavoriteFragmentDirections


class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>(),Navigator {
    private lateinit var preferenceManager: PreferenceManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel=viewModel
        viewDataBinding.lifecycleOwner=this
        preferenceManager= PreferenceManager(requireContext())
        observeViewModel()
        viewModel.navigator=this
    }

    private fun observeViewModel() {
        viewModel.locationMode.observe(viewLifecycleOwner) { mode ->
            updateLocationModeRadioButton(mode)
        }
        viewModel.selectedLanguage.observe(viewLifecycleOwner) { lang ->
            updateLanguageRadioButton(lang)
        }
        viewModel.selectedWindSpeedUnit.observe(viewLifecycleOwner) { windSpeed ->
            updateWindSpeedRadioButton(windSpeed)
        }
        viewModel.selectedTemperatureUnit.observe(viewLifecycleOwner) { tempUnit ->
            updateTempUnitRadioButton(tempUnit)
        }
    }

    private fun updateTempUnitRadioButton(tempUnit: String?) {
        when(tempUnit){
            "Celsius"->viewDataBinding.radioCelsius.isChecked=true
            "Fahrenheit"->viewDataBinding.radioFahrenheit.isChecked=true
            "Kelvin"->viewDataBinding.radioKelvin.isChecked=true
        }
    }

    private fun updateWindSpeedRadioButton(windSpeed: String){
        when (windSpeed){
            "Meter/Sec"-> viewDataBinding.radioMeterSec.isChecked=true
            "Miles/Hour"->viewDataBinding.radioMilesHour.isChecked=true
        }
    }

    private fun updateLanguageRadioButton(language: String) {
        when (language) {
            "English" -> viewDataBinding.radioEnglish.isChecked = true
            "Arabic" -> viewDataBinding.radioArabic.isChecked = true
        }
    }

    private fun updateLocationModeRadioButton(mode: String) {
        when (mode) {
            "GPS" -> viewDataBinding.radioGps.isChecked = true
            "Map" -> viewDataBinding.radioMap.isChecked = true
        }
    }
    override fun getFragmentView(): View {
        return viewDataBinding.root
    }

    override fun initViewModel(): SettingViewModel {
        val settingViewModel: SettingViewModel by viewModels {
            SettingViewModelFactory(PreferenceManager(requireContext()))
        }
        return settingViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun navigateToMapSelectionFragment() {
        findNavController().navigate(R.id.mapSelectionFragment)
    }

    override fun navigateToHomeFragment() {
        findNavController().navigate(R.id.homeFragment)
    }

}