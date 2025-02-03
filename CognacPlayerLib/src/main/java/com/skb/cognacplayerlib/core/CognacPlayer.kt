package com.skb.cognacplayerlib.core

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.skb.cognacplayerlib.data.CognacData
import timber.log.Timber

sealed class MediaState {
    data object Idle: MediaState()
    data object Play: MediaState()
    data object RenderedFirstFrame: MediaState()
    data object Pause: MediaState()
    data object Stop: MediaState()
    data object Complete: MediaState()
    data class Error(val error: PlaybackException): MediaState()
}

@UnstableApi
class CognacPlayer(
    private val context: Context,
    hasAudioFocus: Boolean
) {
    private val TAG = "CognacPlayer"
    private val player: ExoPlayer = ExoPlayer.Builder(context)
        .setTrackSelector(DefaultTrackSelector(context))
        .setLoadControl(
            DefaultLoadControl.Builder()
                .setBufferDurationsMs(
                    DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
                    DefaultLoadControl.DEFAULT_MAX_BUFFER_MS,
                    DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS / 2,
                    DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS / 2
                ).build())
        .setMediaSourceFactory(
            DefaultMediaSourceFactory(
                DefaultHttpDataSource.Factory()
                    .setUserAgent("CognacPlayer")
                    .setAllowCrossProtocolRedirects(true))
        )
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                .setUsage(C.USAGE_MEDIA)
                .build(),
            hasAudioFocus
        )
        .build()

    init {
        Timber.d(TAG, "CognacPlayer init")
    }

    fun setMediaItem(cognacData: CognacData) {
        Timber.d(TAG, "CognacData : $cognacData")
        if (player.isPlaying) player.stop()

        val drmConfiguration: MediaItem.DrmConfiguration? =
            cognacData.drmScheme?.let { Util.getDrmUuid(it) }?.let { uuid ->
                cognacData.drmUri?.let { uri ->
                    MediaItem.DrmConfiguration.Builder(uuid).setLicenseUri(uri).build()
                } ?: run {
                    MediaItem.DrmConfiguration.Builder(uuid).build()
                }
            }
        val mediaItemBuilder = MediaItem.Builder().apply {
            setUri(cognacData.streamUri)
            setMimeType(cognacData.mimeType)
            setDrmConfiguration(drmConfiguration)
        }.build()

        player.setMediaItem(mediaItemBuilder)
        player.prepare()
        cognacData.lastPosition?.let { position ->
            player.seekTo(position)
        }
        player.playWhenReady = true
    }

    fun getPlayer(): ExoPlayer = player
    fun play() = player.play()
    fun pause() = player.pause()
    fun stop() = player.stop()
    fun seekTo(position: Long) = player.seekTo(position)
    fun setPlaybackSpeed(speed: Float) = player.setPlaybackSpeed(speed)
    fun isPlaying() = player.isPlaying

    fun addListener(listener: Player.Listener) {
        player.addListener(listener)
    }

    fun removeListener(listener: Player.Listener) {
        player.removeListener(listener)
    }

    fun hasSubtitleLists(): MutableList<String>? {
        val lists: MutableList<String> = mutableListOf()
        player.currentTracks.groups.forEachIndexed { groupIndex, trackGroup ->
            for (trackIndex in 0 until trackGroup.length) {
                val format = trackGroup.getTrackFormat(trackIndex)
                if (format.sampleMimeType == MimeTypes.TEXT_VTT
                    || format.sampleMimeType == MimeTypes.APPLICATION_MEDIA3_CUES) {
                    format.language?.let { code ->
                        lists.add(code)
                    }
                }
            }
        }
        Timber.d(TAG, "CognacPlayer hasSubtitleLists : $lists")
        return if (lists.isEmpty()) null else lists
    }

    fun updateLanguage(languageCode: String) {
        Timber.d(TAG, "updateLanguage : $languageCode")
        val buildUpon = player.trackSelectionParameters.buildUpon()
        player.trackSelectionParameters =
            buildUpon.setPreferredTextLanguage(languageCode).build()
    }

    fun updateMuteState(isMute: Boolean) {
        val volume = if (isMute) 0f else 1f
        player.volume = volume
    }

    fun release() {
        Timber.d(TAG, "CognacPlayer release")
        player.release()
    }
}