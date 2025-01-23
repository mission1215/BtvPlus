package com.skb.bourbon_network.repository

import com.skb.bourbon_network.network.media.MediaItem
import javax.inject.Inject

class MediaApiRepository @Inject constructor() {
    fun getMediaItems(): MediaItem { // API 호출하여 가져오는 부분
        android.util.Log.d("nhlee", "getMediaItems")
        return MediaItem("1", "test1", "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8")
    }
}