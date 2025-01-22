package com.skb.btvplus.presenter.screen.media

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skb.btvdomainlib.di.DefaultDispatcher
import com.skb.btvdomainlib.di.IoDispatcher
import com.skb.btvdomainlib.di.MainDispatcher
import com.skb.btvdomainlib.network.UiState
import com.skb.btvdomainlib.network.media.MediaItem
import com.skb.btvdomainlib.usecsae.MediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaUseCase: MediaUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher, // CPU 집중 작업
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,           // IO 작업 (네트워크/파일)
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,        // UI 작업
) : ViewModel() {
    private val _mediaItem = MutableStateFlow<UiState<MediaItem>?>(null)
    val mediaItem: StateFlow<UiState<MediaItem>?> = _mediaItem.asStateFlow()
    
    init {
        viewModelScope.launch {
            _mediaItem.value = mediaUseCase.getMediaItems()
        }
    }
}