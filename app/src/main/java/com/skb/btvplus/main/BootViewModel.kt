package com.skb.btvplus.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skb.btvdomainlib.di.DefaultDispatcher
import com.skb.btvdomainlib.di.IoDispatcher
import com.skb.btvdomainlib.di.MainDispatcher
import com.skb.btvdomainlib.network.UiState
import com.skb.btvdomainlib.usecsae.BootUseCase
import com.skb.mytvlibrary.server.service.heb.RespBootSettingInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Boot view model
 *
 * @property restApi
 * @property bootUseCase
 * @property defaultDispatcher
 * @property ioDispatcher
 * @property mainDispatcher
 * @constructor Create empty Boot view model
 */
sealed class NavigationEvent {
    object NavigateToHome : NavigationEvent()
}

@HiltViewModel
class BootViewModel @Inject constructor(
    private val bootUseCase: BootUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher, // CPU 집중 작업
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,           // IO 작업 (네트워크/파일)
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,        // UI 작업
) : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    private val _bootConfig = MutableStateFlow<UiState<RespBootSettingInfo>>(UiState.Loading)
    val bootConfig: StateFlow<UiState<RespBootSettingInfo>> = _bootConfig.asStateFlow()

    init {
        Timber.d("BootViewModel init()")
        viewModelScope.launch {
            val bootConfig = async { bootUseCase.getBootSettings() }.await()
            _bootConfig.emit(bootConfig)
            _navigationEvent.emit(NavigationEvent.NavigateToHome)
        }
    }
}