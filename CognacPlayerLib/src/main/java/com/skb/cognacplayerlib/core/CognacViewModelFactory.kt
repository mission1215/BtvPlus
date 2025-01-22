package com.skb.cognacplayerlib.core

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.UnstableApi
import com.skb.cognacplayerlib.ui.CognacViewModel

@OptIn(UnstableApi::class)
class CognacViewModelFactory(private val player: CognacPlayer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CognacViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CognacViewModel(player) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}