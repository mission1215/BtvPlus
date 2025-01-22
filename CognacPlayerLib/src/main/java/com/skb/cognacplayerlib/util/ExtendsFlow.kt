package com.skb.cognacplayerlib.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow

suspend fun <T> MutableSharedFlow<T>.sendEvent(value: T) {
    this.emit(value)
}

suspend fun <T> Flow<T>.observe(collector: FlowCollector<T>) {
    this.collect(collector)
}