package com.example.weatherforecastapplication.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecastapplication.ui.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel<N>:ViewModel() {
/*     protected val _resultState = MutableStateFlow<ResultState?>(ResultState.Loading)
     val resultState: StateFlow<ResultState?>
          get() = _resultState

     protected fun updateResultState(newState: ResultState) {
          _resultState.value = newState
     }*/

     val messageLiveData = MutableLiveData<Boolean>()
}