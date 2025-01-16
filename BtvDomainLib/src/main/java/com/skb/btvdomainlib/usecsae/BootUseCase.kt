package com.skb.btvdomainlib.usecsae

import com.skb.btvdomainlib.network.UiState
import com.skb.btvdomainlib.repository.BootApiRepository
import com.skb.mytvlibrary.server.service.heb.RespBootSettingInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BootUseCase @Inject constructor(
    private val bootApiRepository: BootApiRepository
) {
    suspend fun getBootSettings(): UiState<RespBootSettingInfo> {
        return bootApiRepository.getBootSettings()
    }
}
