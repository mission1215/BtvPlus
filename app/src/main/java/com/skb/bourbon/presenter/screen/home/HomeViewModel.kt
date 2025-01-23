package com.skb.bourbon.presenter.screen.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skb.bourbon_network.di.DefaultDispatcher
import com.skb.bourbon_network.di.IoDispatcher
import com.skb.bourbon_network.di.MainDispatcher
import com.skb.bourbon_network.network.UiState
import com.skb.bourbon_network.network.carbon.ResponseListShelfInfoResDto
import com.skb.bourbon_network.network.carbon.ResponseListShelfResDto
import com.skb.bourbon_network.repository.HomeApiRepository
import com.skb.bourbon.main.NavigationEvent
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
 * Detail landing item
 *
 * @property id
 * @constructor Create empty Detail landing item
 */
data class HomeNavItem(
    var id: String? = null
)

/**
 * Home view model
 *
 * @property defaultDispatcher
 * @property ioDispatcher
 * @property mainDispatcher
 * @constructor Create empty Home view model
 */
@SuppressLint("AutoboxingStateCreation")
@HiltViewModel
class HomeViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher, // CPU 집중 작업
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,           // IO 작업 (네트워크/파일)
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,        // UI 작업
    private val homeApiRepository: HomeApiRepository,
) : ViewModel() {
    private val navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEventState: SharedFlow<NavigationEvent> get() = navigationEvent.asSharedFlow()
    fun updateNavigationEvent(event: NavigationEvent) = run {
        viewModelScope.launch {
            navigationEvent.emit(event)
        }
    }

    private val _tabs = MutableStateFlow<UiState<ResponseListShelfInfoResDto>>(UiState.Loading)
    val tabState: StateFlow<UiState<ResponseListShelfInfoResDto>> get() = _tabs.asStateFlow()
    fun updateTabs(tabs: UiState<ResponseListShelfInfoResDto>) = run {
        viewModelScope.launch {
            _tabs.emit(tabs)
        }
    }

    private val selectedTabIndex = MutableStateFlow<Int>(0)
    val selectedTabIndexState: StateFlow<Int> get() = selectedTabIndex.asStateFlow()
    fun updateSelectedTabIndex(index: Int) = run {
        viewModelScope.launch {
            selectedTabIndex.emit(index)
        }
    }

    private val contents = MutableStateFlow<UiState<ResponseListShelfResDto>>(UiState.Loading)
    val contentsState: StateFlow<UiState<ResponseListShelfResDto>> get() = contents.asStateFlow()
    fun updateContents(content: UiState<ResponseListShelfResDto>) = run {
        viewModelScope.launch {
            contents.emit(content)
        }
    }

    init {
        Timber.d("requestGetShelfInfo")
        viewModelScope.launch {
            val response = async { homeApiRepository.requestGetShelfInfo() }.await()
            updateTabs(response)
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
            updateContents(response)
        }
    }

    fun sendEvent(navigationEvent: NavigationEvent) {
        viewModelScope.launch {
            Timber.d("sendEvent $navigationEvent")
            updateNavigationEvent(navigationEvent)
        }

    }
}