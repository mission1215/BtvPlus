package com.skb.cognacplayerlib.core

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi

class CognacPlayerFactory {
    @OptIn(UnstableApi::class)
    fun create(context: Context, hasAudioFocus: Boolean): CognacPlayer {
        return CognacPlayer(context, hasAudioFocus)
    }
}