package com.skb.btvplus.navigator

import androidx.lifecycle.ViewModel
import com.skb.btvdomainlib.di.DefaultDispatcher
import com.skb.btvdomainlib.di.IoDispatcher
import com.skb.btvdomainlib.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher, // CPU 집중 작업
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,           // IO 작업 (네트워크/파일)
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,        // UI 작업
) : ViewModel() {
    var landingItem : LandingItem? = null
}