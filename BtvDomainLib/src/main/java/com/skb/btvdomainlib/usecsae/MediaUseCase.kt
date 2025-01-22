package com.skb.btvdomainlib.usecsae

import com.skb.btvdomainlib.network.UiState
import com.skb.btvdomainlib.network.media.MediaItem
import com.skb.btvdomainlib.repository.MediaApiRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaUseCase @Inject constructor(
    private val mediaApiRepository: MediaApiRepository
) {
    suspend fun getMediaItems(): UiState<MediaItem> {
        return try {
            val mediaItem = mediaApiRepository.getMediaItems()
            UiState.Success(mediaItem)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "UnKnown Error")
        }
    }
}