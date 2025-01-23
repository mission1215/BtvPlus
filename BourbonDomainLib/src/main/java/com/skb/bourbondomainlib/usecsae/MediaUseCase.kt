package com.skb.bourbondomainlib.usecsae

import com.skb.bourbondomainlib.network.UiState
import com.skb.bourbondomainlib.network.media.MediaItem
import com.skb.bourbondomainlib.repository.MediaApiRepository
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