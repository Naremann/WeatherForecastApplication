/*
package com.example.weatherforecastapplication.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.base.BaseFragment
import com.example.weatherforecastapplication.databinding.FragmentSettingBinding
import com.example.weatherforecastapplication.data.db.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>(), Navigator {
    private lateinit var preferenceManager:PreferenceManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceManager=PreferenceManager(requireContext())
        viewDataBinding.viewModel = viewModel
       // viewDataBinding.lifecycleOwner = this
        checkRadioButtons()
        viewModel.navigator = this
    }

    private fun checkRadioButtons() {
        updateWindSpeedRadioButton()
        updateLanguageRadioButton()
        updateLocationModeRadioButton()
        updateTempUnitRadioButton()
    }

    private fun updateTempUnitRadioButton() {
        when (preferenceManager.getTempUnit()) {
            "Celsius" -> viewDataBinding.radioCelsius.isChecked = true
            "Fahrenheit" -> viewDataBinding.radioFahrenheit.isChecked = true
            "Kelvin" -> viewDataBinding.radioKelvin.isChecked = true
        }
    }

    private fun updateWindSpeedRadioButton() {
        Log.e("TAG", "updateWindSpeedRadioButton: "+preferenceManager.getWindSpeed())
        when (preferenceManager.getWindSpeed()) {
            "meter/sec" -> viewDataBinding.radioMeterSec.isChecked = true
            "miles/hour" -> viewDataBinding.radioMilesHour.isChecked = true
        }
    }

    private fun updateLanguageRadioButton() {
        when (preferenceManager.getLanguage()) {
            "English" -> viewDataBinding.radioEnglish.isChecked = true
            "Arabic" -> viewDataBinding.radioArabic.isChecked = true
        }
    }

    private fun updateLocationModeRadioButton() {
        when (preferenceManager.getLocationMode()) {
            "GPS" -> viewDataBinding.radioGps.isChecked = true
            "Map" -> viewDataBinding.radioMap.isChecked = true
        }
    }

    override fun getFragmentView(): View {
        return viewDataBinding.root
    }

    override fun initViewModel(): SettingViewModel {
        return ViewModelProvider(this)[SettingViewModel::class.java]
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
*/

package com.example.weatherforecastapplication.ui.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.base.BaseFragment
import com.example.weatherforecastapplication.data.db.PreferenceManager
import com.example.weatherforecastapplication.databinding.FragmentSettingBinding
import com.example.weatherforecastapplication.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>(), Navigator {
    @Inject lateinit var preferenceManager: PreferenceManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
        observeToLiveData()
        checkRadioButtons()
        viewModel.navigator = this
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                refreshMainActivity()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

    private fun refreshMainActivity() {
        requireActivity().finish()
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        checkRadioButtons()
    }

    override fun onPause() {
        super.onPause()
        checkRadioButtons()
    }



    private fun checkRadioButtons() {
        updateWindSpeedRadioButton()
        updateLanguageRadioButton()
        updateLocationModeRadioButton()
        updateTempUnitRadioButton()
    }

    private fun updateTempUnitRadioButton() {
        Log.e("TAG", "updateTempUnitRadioButton: "+preferenceManager.getTempUnit() )
        when (preferenceManager.getTempUnit()) {
            "Celsius" -> viewDataBinding.radioCelsius.isChecked = true
            "Fahrenheit" -> viewDataBinding.radioFahrenheit.isChecked = true
            "Kelvin" -> viewDataBinding.radioKelvin.isChecked = true
        }
    }

    private fun updateWindSpeedRadioButton() {
        when (preferenceManager.getWindSpeed()) {
            "meter/sec" -> viewDataBinding.radioMeterSec.isChecked = true
            "miles/hour" -> viewDataBinding.radioMilesHour.isChecked = true
        }
    }

    private fun updateLanguageRadioButton() {
        when (preferenceManager.getLanguage()) {
            "English" -> viewDataBinding.radioEnglish.isChecked = true
            "Arabic" -> viewDataBinding.radioArabic.isChecked = true
        }
    }

    private fun updateLocationModeRadioButton() {
        when (preferenceManager.getLocationMode()) {
            "GPS" -> viewDataBinding.radioGps.isChecked = true
            "Map" -> viewDataBinding.radioMap.isChecked = true
        }
    }




    override fun getFragmentView(): View {
        return viewDataBinding.root
    }

    override fun initViewModel(): SettingViewModel {
        return ViewModelProvider(this)[SettingViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun navigateToMapSelectionFragment() {
        findNavController().navigate(R.id.mapSelectionFragment)
    }


}

