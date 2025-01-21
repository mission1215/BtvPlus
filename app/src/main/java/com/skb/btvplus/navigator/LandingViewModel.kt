package com.skb.btvplus.navigator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skb.btvdomainlib.di.DefaultDispatcher
import com.skb.btvdomainlib.di.IoDispatcher
import com.skb.btvdomainlib.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher, // CPU 집중 작업
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,           // IO 작업 (네트워크/파일)
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,        // UI 작업
) : ViewModel() {
    private val _landingItem = MutableSharedFlow<LandingItem>()
    val landingItemState: SharedFlow<LandingItem> get() = _landingItem
    fun updateLandingItem(landingItem: LandingItem) = run {
        viewModelScope.launch {
            _landingItem.emit(landingItem)
        }
    }
}