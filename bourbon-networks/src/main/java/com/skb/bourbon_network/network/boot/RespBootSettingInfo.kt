package com.skb.mytvlibrary.server.service.heb

import com.google.gson.annotations.SerializedName
import timber.log.Timber
import java.io.Serializable

class RespBootSettingInfo {
    @SerializedName("code")            var code: String? = null
    @SerializedName("message")         var message: String? = null
    @SerializedName("if_id")           var ifId: String? = null
    @SerializedName("request_time")    var requestTime: String? = null
    @SerializedName("response_time")   var responseTime: String? = null
    @SerializedName("data")            var data: Data? = null

    class Data {
        @SerializedName("release_date") var releaseDate: String? = null
        @SerializedName("mode")         var mode: String? = null
        @SerializedName("settings")     var settings: BootConfigurations? = null
        @SerializedName("aggregators")  var aggregators: List<Aggregators>? = null
        @SerializedName("server_info")  var serverInfo: List<ServerInfo>? = null
    }

    class Aggregators {
        @SerializedName("name")        var name: String? = null
        @SerializedName("code")        var code: String? = null
        @SerializedName("packagename") var packagename: String? = null
        @SerializedName("deep_link")   var deepLink: String? = null
    }

    class ServerInfo : Serializable {
        @SerializedName("id")      var id: String? = null
        @SerializedName("address") var address: String? = null
        @SerializedName("port")    var port: String? = null

        fun getBaseUrl(withLastSlash: Boolean = true): String {
            val sb = StringBuffer(address ?: "")
            port?.takeIf { it.isNotBlank() }?.let { sb.append(":").append(it) }
            if (withLastSlash) sb.append("/")
            val baseUrl = sb.toString()
            Timber.tag("ServerInfo").d("getBaseUrl() id:$id, baseUrl:$baseUrl")
            return baseUrl
        }
    }
}