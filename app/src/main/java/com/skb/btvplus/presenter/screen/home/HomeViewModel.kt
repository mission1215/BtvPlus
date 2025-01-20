package com.skb.btvplus.presenter.screen.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skb.btvdomainlib.di.DefaultDispatcher
import com.skb.btvdomainlib.di.IoDispatcher
import com.skb.btvdomainlib.di.MainDispatcher
import com.skb.btvdomainlib.network.UiState
import com.skb.btvdomainlib.network.carbon.ResponseListShelfInfoResDto
import com.skb.btvdomainlib.network.carbon.ResponseListShelfResDto
import com.skb.btvdomainlib.repository.HomeApiRepository
import com.skb.btvplus.navigator.LandingItem
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
 * Home view model
 *
 * @property defaultDispatcher
 * @property ioDispatcher
 * @property mainDispatcher
 * @constructor Create empty Home view model
 */
sealed class NavigationEvent {
    class NavigateToDetail(ladingItem: LandingItem) : NavigationEvent()
}

@SuppressLint("AutoboxingStateCreation")
@HiltViewModel
class HomeViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher, // CPU 집중 작업
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,           // IO 작업 (네트워크/파일)
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,        // UI 작업
    private val homeApiRepository: HomeApiRepository,
) : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    private val _tabs = MutableStateFlow<UiState<ResponseListShelfInfoResDto>>(UiState.Loading)
    val tabs: StateFlow<UiState<ResponseListShelfInfoResDto>> = _tabs.asStateFlow()

    private val _selectedTabIndex = MutableStateFlow<Int>(0)
    val selectedTabIndexState: StateFlow<Int> = _selectedTabIndex.asStateFlow()
    fun setSelectedTabIndex(index: Int) = run { _selectedTabIndex.value = index }

    private val _contents = MutableStateFlow<UiState<ResponseListShelfResDto>>(UiState.Loading)
    val contents: StateFlow<UiState<ResponseListShelfResDto>> = _contents.asStateFlow()

    init {
        Timber.d("requestGetShelfInfo")
        viewModelScope.launch {
            val a = async { homeApiRepository.requestGetShelfInfo() }
            _tabs.emit(a.await())
            requestGetExternalShelf(0)
        }
    }

    fun requestGetExternalShelf(index: Int) = viewModelScope.launch {
        val shelfId = (_tabs.value as? UiState.Success)?.data?.data?.getOrNull(index)?.shelfId ?: ""
        requestGetExternalShelf(shelfId)
    }

    fun requestGetExternalShelf(shelfId: String?) = viewModelScope.launch {
        shelfId?.let {
            val response = homeApiRepository.requestGetExternalShelf(it)
            when (response) {
                is UiState.Error -> {
                    Timber.d("requestGetExternalShelf Error, ${response.message}")
                }

                is UiState.Success -> {
                    Timber.d("requestGetExternalShelf Success, ${response.data}")
                }

                UiState.Loading -> {}
            }
            _contents.emit(response)
        }
    }

    fun sendEvent(navigationEvent: NavigationEvent) {
        viewModelScope.launch {
            Timber.d("sendEvent $navigationEvent")
            _navigationEvent.emit(navigationEvent)
        }

    }
}