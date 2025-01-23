package com.skb.bourbondomainlib.network.carbon

import com.google.gson.annotations.SerializedName

/**
 * ${A.TV}
 * Created by SK_BroadBand
 * Date: 2023/01/27
 * Description:
 */
data class ResponseListShelfInfoResDto(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("data") val data: List<ShelfInfoResDto>? = null,
)
