package com.skb.cognacplayerlib.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

private val TAG = "PlaybackReceiver"

sealed class Action {
    data object Left : Action()
    data object Center : Action()
    data object Right : Action()
}

object PlaybackActionBus {
    private val _actions = MutableSharedFlow<Action>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val actions = _actions.asSharedFlow()

    suspend fun emitAction(action: Action) {
        _actions.tryEmit(action)
        Timber.d(TAG, "emitAction : $action")
    }
}

class PlaybackReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = when (intent.action) {
            Action.Left.toString() -> Action.Left
            Action.Center.toString() -> Action.Center
            Action.Right.toString() -> Action.Right
            else -> null
        }
        action?.let {
            MainScope().launch {
                PlaybackActionBus.emitAction(action = it)
            }
        }
    }
}