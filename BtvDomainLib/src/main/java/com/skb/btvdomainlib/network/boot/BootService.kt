package com.skb.btvdomainlib.network.boot

import com.skb.btvdomainlib.extentsions.getHost
import com.skb.mytvlibrary.server.service.heb.RespBootSettingInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface BootService {
    companion object {
        private const val TAG = "BootService"

        private const val PRODUCTION_URL = "https://agw.sk-iptv.com:8087/"
        private const val STAGING_URL = "https://agw-stg.sk-iptv.com:8087/"

        fun getBaseUrl(isStaging: Boolean) = if (isStaging) STAGING_URL else PRODUCTION_URL

        fun getTrustedHosts(isStaging: Boolean): List<String?> {
            return listOf(getBaseUrl(isStaging).getHost())
        }
    }

    /**
     * IF-HEB-BOOT-001
     * HEB Launcher 부팅 시 설정 정보 제공
     */
    @GET("boot/v1/info")
    suspend fun requestBootSettings(
        @HeaderMap reqHeaderMap: HashMap<String?, String?>,
        @QueryMap reqQueryMap: HashMap<String?, String?>
    ): Response<RespBootSettingInfo>
}