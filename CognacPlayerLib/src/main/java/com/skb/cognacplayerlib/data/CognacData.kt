package com.skb.cognacplayerlib.data

import androidx.media3.common.MimeTypes

data class CognacData(
    val streamUri: String,
    var drmUri: String? = null,
    var drmScheme: String? = null,
    var mimeType: String = MimeTypes.APPLICATION_M3U8,
    var lastPosition: Long? = null
)
