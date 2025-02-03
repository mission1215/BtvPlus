package com.skb.cognacplayerlib.ui

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_IDLE
import androidx.media3.common.Player.STATE_READY
import androidx.media3.common.VideoSize
import androidx.media3.common.text.CueGroup
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.skb.cognacplayerlib.core.CognacPlayer
import com.skb.cognacplayerlib.core.MediaState
import com.skb.cognacplayerlib.core.MediaState.*
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
    private val TAG = "CognacViewModel"

    private val _viewEffect = MutableSharedFlow<ViewEffect>()
    val viewEffect = _viewEffect.asSharedFlow()

    private val _isMute = MutableStateFlow(false)
    val isMute: StateFlow<Boolean> = _isMute.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _state = MutableStateFlow<MediaState>(Idle)
    val state: StateFlow<MediaState> = _state.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _subTitle = MutableStateFlow<String?>(null)
    val subTitle: StateFlow<String?> = _subTitle.asStateFlow()

    private var playerView: PlayerView? = null
    private var job: Job? = null

    fun sendEvent(viewEffect: ViewEffect) = viewModelScope.launch {
        _viewEffect.sendEvent(viewEffect)
    }

    fun setPlayerView(view: PlayerView) {
        playerView = view
    }

    fun setMediaItem(mediaItemData: CognacData) {
        _state.value = Idle
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
        _state.value = if (player.isPlaying()) {
            player.pause()
            Pause
        } else {
            player.play()
            Play
        }
    }

    fun stop() {
        playerView?.keepScreenOn = false
        player.stop()
        _state.value = Pause
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

    fun hasSubtitleLists(): MutableList<String>? {
        return player.hasSubtitleLists()
    }

    fun getPlayer(): ExoPlayer = player.getPlayer()

    fun release() {
        Timber.i(TAG, "release")
        job?.cancel()
        playerView?.keepScreenOn = false
        playerView = null
        player.removeListener(listener)
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

    private val listener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            Timber.d(TAG, "playbackState : $playbackState")
            _state.value = when (playbackState) {
                STATE_READY -> {
                    Play
                }
                STATE_ENDED -> {
                    Complete
                }
                else -> Idle
            }
            if (playbackState == STATE_READY) {
                _isPlaying.value = true
            } else if (playbackState == STATE_IDLE || playbackState == STATE_ENDED) {
                _isPlaying.value = false
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            Timber.d(TAG, "errorCode : ${error.errorCode}, errorCodeName : ${error.errorCodeName}")
            _state.value = Error(error)
            _isPlaying.value = false
        }

        override fun onCues(cueGroup: CueGroup) {
            super.onCues(cueGroup)
            _subTitle.value = ""
            cueGroup.cues.forEachIndexed { index, cue ->
                _subTitle.value = cue.text.toString()
            }
        }

        override fun onRenderedFirstFrame() {
            Timber.d(TAG, "onRenderedFirstFrame")
            super.onRenderedFirstFrame()
            _state.value = RenderedFirstFrame
        }

        override fun onVideoSizeChanged(videoSize: VideoSize) {
            super.onVideoSizeChanged(videoSize)
            Timber.d(TAG, "onVideoSizeChanged : $videoSize")
        }
    }

    init {
        player.addListener(listener)
    }

    sealed class ViewEffect() {
        data class PictureInPitureMode(val resource: PipResource) : ViewEffect()
    }
}