package com.skb.bourbon_network.network.carbon

import com.google.gson.annotations.SerializedName

/**
 * Response list shelf res dto
 * 선반 정보
 *
 * @property count
 * @property data
 * @constructor Create empty Response list shelf res dto
 */
data class ResponseListShelfResDto(
    @SerializedName("count") val count: Int = 0,
    @SerializedName("data") val data: ShelfResData?,
)

/**
 * ShelfResData
 *
 * @property items
 */
data class ShelfResData(
    @SerializedName("items") var items: List<ShelfResItemEntry>?,
)

/**
 * ShelfResItemEntry
 *
 * @property _cardType
 * @property item
 */
data class ShelfResItemEntry(
    @SerializedName("cardType") private val _cardType: String? = "",
    @SerializedName("item") val item: ShelfResItem?,
    @SerializedName("sort") val sort: Int? = 0,
)

/**
 * ShelfResItem
 *
 * @property channelId
 * @property channelName
 * @property _channelType
 * @property curationName
 * @property _curationType
 * @property _apiType
 * @property cardId
 * @property apiCard Api Card Data
 * @property serviceOpenTime
 * @property serviceCloseTime
 * @property cardName
 * @property showRank
 * @property content Channel data
 * @property items Race, Curation Data
 * @property videos Youtube data
 * @property banners Banner Data
 */
data class ShelfResItem(
    @SerializedName("cardId") val cardId: String? = null,

    //Program. Channel
    @SerializedName("channelId") val channelId: String? = null,

    //Shorts
    @SerializedName("cardName") val cardName: String? = null,
    @SerializedName("cardSubName") val cardSubName: String? = null,
    @SerializedName("cardType") val cardType: String? = null, //BOX_OFFICE, MOVIE_VOD, SERIES
    @SerializedName("contentType") val contentType: String? = null, // MOVIE, DRAMA, ANIMATION
    @SerializedName("channelType") private val _channelType: String? = null,
    @SerializedName("channelName") val channelName: String? = null,
//
//    //Curation
//    @SerializedName("curationName") val curationName: String? = null,
    @SerializedName("curationType") private val _curationType: String? = null,

    //API
    @SerializedName("apiType") private val _apiType: String? = null,
    @SerializedName("object") val apiCard: ItemRedisEntry? = null, //ApiCard Data
    @SerializedName("serviceOpenTime") val serviceOpenTime: String? = null,
    @SerializedName("serviceCloseTime") val serviceCloseTime: String? = null,

    @SerializedName("showCardName") val showCardName: Boolean? = false,

    //Youtube
    @SerializedName("showRank") val showRank: Boolean? = false, //랭킹 노출 여부
    @SerializedName("isSingleVideo") val isSingleVideo: Boolean? = true, //단일 편성 여부

    // kino_rank , Ai Review
    @SerializedName("rankType") val rankType: String? = null,
    @SerializedName("blockTitle") val blockTitle: String? = null,
    @SerializedName("blockSubTitle") val blockSubTitle: String? = null,

    // Ai Review
    @SerializedName("contentTitle") val contentTitle: String? = null,
    @SerializedName("positiveReviews") val positiveReviews: List<String>? = null,
    @SerializedName("negativeReviews") val negativeReviews: List<String>? = null,

    //List
    @SerializedName("contents") var contents: List<ContentItemResDto>? = null, //Channel data
    @SerializedName("items") var items: List<ItemRedisEntry>? = null, //Race, Curation data, shorts
    @SerializedName("videos") var videos: List<VideoRedisEntry>? = null, //Youtube data, Ai Review
    @SerializedName("banners") val banners: List<BannerResDto>? = null, //banner data
    @SerializedName("video") var video: VideoRedisEntry? = null, //Channel data , Ai Review, kino
    @SerializedName("contentViewType") val _contentViewType: String? = null,
)

/**
 * ContentItemResDto
 *
 * @property timeTableId
 * @property id Shots 전용 Id
 * @property mainTitle
 * @property subTitle
 * @property _watLvlCd
 * @property episodeId
 * @property episodeRsluId
 * @property playTimeSec
 * @property episodeNum
 * @property srisId
 * @property _imagePath
 * @property _contentType
 */
data class ContentItemResDto(
    @SerializedName("timeTableId") val timeTableId: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("mainTitle") val mainTitle: String? = null,
    @SerializedName("subTitle") val subTitle: String? = null,
    @SerializedName("watchLevel") val _watchLevel: String? = null,
    @SerializedName("episodeId") val episodeId: String? = null,
    @SerializedName("episodeRsluId") val episodeRsluId: String? = null,
    @SerializedName("playTimeSec") val playTimeSec: String? = null,
    @SerializedName("episodeNum") val episodeNum: String? = null,
    @SerializedName("srisId") val srisId: String? = null,
    @SerializedName("imagePath") val _imagePath: String? = null,
    @SerializedName("contentType") val _contentType: String? = null,
    @SerializedName("isDownload") val _isDownload: String? = null,
    @SerializedName("gameId") private val gameId: String? = null,
    @SerializedName("showAICaster") private val _showAICaster: String? = null,
    @SerializedName("streamSource") val _streamSource: String? = null,
    @SerializedName("streamUrl") val streamUrl: String? = null,
)

/**
 * VideoRedisEntry
 *
 * @property videoId
 * @property linkType
 * @property episodeId
 * @property mainTitle
 * @property thumbnail
 * @property publishedAt
 * @property channelTitle
 */
data class VideoRedisEntry(
    @SerializedName("videoId") val videoId: String?, //Ai Re
    @SerializedName("channelTitle") val channelTitle: String?,// view
    @SerializedName("mainTitle") val mainTitle: String?,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("isVisible") val isVisible: String?,

    @SerializedName("id") val id: String?, //Youtube id
    @SerializedName("linkType") val linkType: String?,
    @SerializedName("epsdId") val episodeId: String?,
    @SerializedName("title") val title: String?,

    //kino
    @SerializedName("kinoId") val kinoId: String? = null,
    @SerializedName("rank") val rank: String? = null,
    //TODO : 서버 작업 후 개발 예정
    @SerializedName("ottImageList") val ottImageList: List<String>? = null,
    @SerializedName("ottList") val ottItem: List<OttBlock>? = null,
)

data class OttBlock(
    @SerializedName("type") private var _type: String? = null,
    @SerializedName("count") val count: String? = null,
    @SerializedName("items") val items: List<OttItem>? = null,
)

/**
 * OttItem
 */
data class OttItem(
    @SerializedName("provider") val provider: String? = null,
    @SerializedName("iconUrl") val iconUrl: String? = null,
    @SerializedName("appUrl") val appUrl: String? = null,
    @SerializedName("price") val price: String? = null,
)

/**
 * APi Card Data
 *
 * @property gameId // golf Card data
 * @property channelId
 * @property channelName
 * @property _channelType
 * @property content
 * @property golfFullScheduleShelfId
 * @property id
 * @property info
 * @property title
 * @property mainTitle
 * @property episodeId
 * @property episodeRsluId
 * @property playTimeSec
 * @property _imagePath
 * @property shelfId //응원선수 설정 시 "종합 순위"선반으로 이동하기 위해 shelfId 필요
 */
data class ItemRedisEntry(
    //Curation , API
    @SerializedName("gameId") val gameId: String? = null,
    @SerializedName("channelId") val channelId: String? = null,
    @SerializedName("channelName") var channelName: String? = null,
    @SerializedName("channelType") private val _channelType: String? = null,
    @SerializedName("content") val content: ContentItemResDto? = null,

    @SerializedName("golfFullScheduleShelfId") val golfFullScheduleShelfId: String? = null,

    //Shorts-Story
    @SerializedName("id") val id: String? = null,
    @SerializedName("itemCardId") val itemCardId: String? = null,
    @SerializedName("info") val info: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("mainTitle") val mainTitle: String? = null,
    @SerializedName("subTitle") val subTitle: String? = null,
    @SerializedName("episodeId") val episodeId: String? = null,
    @SerializedName("episodeRsluId") val episodeRsluId: String? = null,
    @SerializedName("playTimeSec") val playTimeSec: String? = null,
    @SerializedName("streamUrl") val streamUrl: String? = null,

    // Common
    @SerializedName("imagePath") private val _imagePath: String? = null,

    @SerializedName("shelfId") val shelfId: String? = null, //  //응원선수 설정 시 "종합 순위"선반으로 이동하기 위해 shelfId 필요
    @SerializedName("streamSource") private val _streamSource: String? = null,
    @SerializedName("showAiCaster") val _showAiCaster: String? = null,
)