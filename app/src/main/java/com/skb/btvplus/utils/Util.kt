package com.skb.btvplus.utils

import java.sql.Types

/** searchSeconds 는 5분 이하의 콘텐츠의 썸네일 노출로 인하여 300 -> 20으로 호출 */
//fun generateThumbnailUrl(
//    resolution: String? = null,
//    searchSeconds: Int? = null,
//    url: String,
//    streamSource: Types.StreamType? = Types.StreamType.SKB,
//): String {
//    val prefix =
//        if (url.contains("/igs/")) "https://stimage.hanafostv.com:9443" else MyTv.imagePrefix // 오류 수정
//    return when (streamSource) {
//        Types.StreamType.SKB, Types.StreamType.SMR -> {
//            url.replace("#SEARCH_SECONDS#", searchSeconds?.toString() ?: "#SEARCH_SECONDS#")
//                .replace("#RESOLUTION#", resolution ?: "#RESOLUTION#")
//                .let { if (it.startsWith("https://")) it else "$prefix$it" }
//        }
//
//        else -> url
//    }
//}
