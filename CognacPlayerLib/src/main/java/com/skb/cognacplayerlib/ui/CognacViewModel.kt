package com.skb.cognacplayerlib.ui

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.skb.cognacplayerlib.core.CognacPlayer
import com.skb.cognacplayerlib.core.PipResource
import com.skb.cognacplayerlib.data.CognacData
import com.skb.cognacplayerlib.util.sendEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(UnstableApi::class)
class CognacViewModel(private val player: CognacPlayer) : ViewModel() {
    private val TAG = "MediaViewModel"

    private val _viewEffect = MutableSharedFlow<ViewEffect>()
    val viewEffect = _viewEffect.asSharedFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private var playerView: PlayerView? = null
    private var job: Job? = null // Coroutine Job 추가

    fun sendEvent(viewEffect: ViewEffect) = viewModelScope.launch {
        _viewEffect.sendEvent(viewEffect)
    }

    fun setPlayerView(view: PlayerView) {
        playerView = view
    }

    fun setMediaItem(mediaItemData: CognacData) {
        try {
            playerView?.keepScreenOn = true
            player.setMediaItem(mediaItemData)
            runTimeTick()
        } catch (e: Exception) {
            Timber.d(TAG, "setMediaItem error : ${e.message}")
        }
    }

    fun togglePlayState() {
        Timber.i(TAG, "togglePlayState : ${player.isPlaying()}")
        playerView?.keepScreenOn = player.isPlaying()
        if (player.isPlaying()) {
            player.pause()
        } else {
            player.play()
        }
    }

    fun stop() {
        playerView?.keepScreenOn = false
        player.stop()
    }

    fun seekTo(position: Long) {
        Timber.i(TAG, "seekTo : $position")
        player.seekTo(position)
    }

    fun setPlaybackSpeed(speed: Float) {
        Timber.i(TAG, "setPlaybackSpeed : $speed")
        player.setPlaybackSpeed(speed)
    }

    fun updateLanguage(languageCode: String) {
        Timber.i(TAG, "updateLanguage : $languageCode")
        player.updateLanguage(languageCode)
    }

    fun updateRatio(ratio: Int) {
        Timber.i(TAG, "updateRatio : $ratio")
        playerView?.let { view ->
            view.resizeMode = ratio
        }
    }

    fun updateMuteState(isMute: Boolean) {
        Timber.i(TAG, "updateMuteState : $isMute")
        player.updateMuteState(isMute)
    }

    fun getPlayer(): ExoPlayer = player.getPlayer()

    fun release() {
        Timber.i(TAG, "release")
        job?.cancel()
        playerView?.keepScreenOn = false
        playerView = null
        player.release()
        viewModelScope.coroutineContext.cancelChildren()
    }

    private fun runTimeTick() {
        job?.cancel()
        job = viewModelScope.launch {
            flow {
                while (isActive && player.isPlaying()) {
                    emit(Unit)
                    delay(1000)
                }
            }.collect {
                _currentPosition.value = player.getPlayer().currentPosition
                _duration.value = player.getPlayer().duration
            }
        }
    }

    sealed class ViewEffect() {
        data class PictureInPitureMode(val resource: PipResource) : ViewEffect()
    }
}