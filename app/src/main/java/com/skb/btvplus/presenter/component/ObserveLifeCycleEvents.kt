package com.skb.btvplus.presenter.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import timber.log.Timber

@Composable
fun ObserveLifeCycleEvents(
    callbackEvent: (callbackEvent: Lifecycle.Event) -> Unit,
    onDisPoseCallback: () -> Unit,
) {
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    DisposableEffect(lifecycleOwner) {
        Timber.d("DisposableEffect")
        val observer = LifecycleEventObserver { _, event ->
            Timber.d("LifecycleEventObserver, $event")
            callbackEvent(event)
        }
        val lifecycle = lifecycleOwner.value.lifecycle
        lifecycle.addObserver(observer)
        onDispose {
            Timber.d("onDispose")
            lifecycle.removeObserver(observer)
            onDisPoseCallback()
        }
    }
}