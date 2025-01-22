package com.skb.btvdomainlib.repository

import com.skb.btvdomainlib.network.CommonHeader
import com.skb.btvdomainlib.network.CommonHeader.HeaderType
import com.skb.btvdomainlib.network.CommonRequest
import com.skb.btvdomainlib.network.UiState
import com.skb.btvdomainlib.network.boot.BootService
import com.skb.btvdomainlib.network.executeApiCallSync
import com.skb.mytvlibrary.server.service.heb.RespBootSettingInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BootApiRepository @Inject constructor(private val bootService: BootService) {

    suspend fun getBootSettings(): UiState<RespBootSettingInfo> {
        val header = CommonHeader(HeaderType.Base).mBaseMap
        val request = CommonRequest().apply {
            add("v", "1") // app version information
            add("sn", "com.skt.nugu.apollo") // device serial number
        }.mBaseMap
        return executeApiCallSync { bootService.requestBootSettings(header, request) }
    }
}