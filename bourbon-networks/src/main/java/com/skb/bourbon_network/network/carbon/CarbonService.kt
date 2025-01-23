package com.skb.bourbon_network.network.carbon

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CarbonService {
    companion object {
        private const val PRODUCTION_URL = "https://agw.sk-iptv.com:8087/"
        private const val STAGING_URL = "https://agw-stg.sk-iptv.com:8087/"
        val baseUrl: String
            get() = if (true) STAGING_URL else PRODUCTION_URL
    }

    fun getCommonQueryMap(
    ): HashMap<String, String?> {
        return mutableMapOf(
            "osType" to "AOS",
            "appVersion" to "1", // TODO: 추가 version
            "legalAge" to "1999-01-01",
            "birth" to "1999-01-01",
            "gender" to "M",
        ) as HashMap<String, String?>
    }

    /**
     * 상단 탭 리스트 (v2)
     *
     * @param reqHeaderMap
     * @param RequestBody
     * @return
     */
    @GET("carbon/shelf/v2/info")
    suspend fun requestGetShelfInfo(
        @HeaderMap reqHeaderMap: HashMap<String?, String?>,
        @QueryMap reqQueryMap: HashMap<String?, String?>,
    ): Response<ResponseListShelfInfoResDto>

    /**
     * 선반 리스트 정보 조회 (v2)
     *
     * @param reqHeaderMap
     * @param id
     * @param RequestBody
     * @return
     */
    @GET("carbon/shelf/v2/external-items/{id}")
    suspend fun requestGetExternalShelf(
        @HeaderMap reqHeaderMap: HashMap<String?, String?>,
        @Path("id") id: String,
        @QueryMap reqQueryMap: HashMap<String?, String?>,
    ): Response<ResponseListShelfResDto>
}

