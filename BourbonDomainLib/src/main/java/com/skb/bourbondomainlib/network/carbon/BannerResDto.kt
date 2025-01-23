package com.skb.bourbondomainlib.network.carbon

import com.google.gson.annotations.SerializedName

/** ${A.TV} Created by SK_BroadBand Date: 2023/01/27 Description: */

/**
 * Banner ITem
 *
 * @property bannerId default: BN_101
 * @property bannerName default: 이벤트에 응모하세요 ,배너 이름
 * @property assign
 * @property channelId
 * @property timetableId
 * @property linkType default: DEEP_LINK link type, 데이터블록도 이 타입으로 구분합니다.
 *     linkType ==SPORTS_BLOCK 인경우 스포츠블록
 * @property linkValue default: //a.tv.com , 링크 URL
 * @property serviceOpenTime default: 20220101000000 , 배너 노출 시간
 * @property serviceCloseTime default: 20251201000000, 배너 노출 시간
 * @property _imagePath
 * @property image
 * @property height
 * @property size
 * @property sort
 */
data class BannerResDto(
    @SerializedName("cardId") val cardId: String = "",
    @SerializedName("cardName") val cardName: String? = null,
    @SerializedName("linkType") val linkType: String? = null,
    @SerializedName("assign") val assign: String? = null,
    @SerializedName("channelId") val channelId: String? = null,
    @SerializedName("timetableId") val timetableId: String? = null,
    @SerializedName("linkValue") val linkValue: String? = null,
    @SerializedName("serviceOpenTime") val serviceOpenTime: String? = null,
    @SerializedName("serviceCloseTime") val serviceCloseTime: String? = null,
    @SerializedName("imagePath") private val _imagePath: String? = null,
    @SerializedName("height") var height: Int = 0,
    @SerializedName("size") val size: String? = null,
    @SerializedName("sort") val sort: String? = null,
    @SerializedName("episodeId") val episodeId: String? = null,
)