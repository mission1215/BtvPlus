package com.skb.btvplus.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SharedData() {

}

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private val _sharedData = MutableStateFlow<SharedData?>(null)
    val sharedData: StateFlow<SharedData?> get() = _sharedData
    fun updateSharedData(data: SharedData) = run {
        _sharedData.value = data
    }
}