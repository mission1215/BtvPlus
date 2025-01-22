package com.skb.btvdomainlib.usecsae

import com.skb.btvdomainlib.network.UiState
import com.skb.btvdomainlib.repository.BootApiRepository
import com.skb.mytvlibrary.server.service.heb.BootConfigurations
import com.skb.mytvlibrary.server.service.heb.RespBootSettingInfo
import com.skb.mytvlibrary.utils.BootUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BootUseCase @Inject constructor(
    private val bootApiRepository: BootApiRepository,
    private val bootUtil: BootUtil,
) {
    suspend fun getBootSettings(): UiState<RespBootSettingInfo> {
        val bootConfig = bootApiRepository.getBootSettings()
        when (bootConfig) {
            is UiState.Success -> {
                // coroutineScope를 사용하여 async 작업을 동기적으로 처리
                coroutineScope {
                    val configDeferred =
                        async { saveBootConfigurations(bootConfig.data.data?.settings) }
                    val serverDeferred =
                        async { saveServerList(bootConfig.data.data?.serverInfo ?: emptyList()) }

                    // 두 작업이 완료될 때까지 대기
                    configDeferred.await()
                    serverDeferred.await()
                }
                Timber.d("BootViewModel save Complete serverList")
            }

            is UiState.Error -> {
                Timber.d("BootViewModel getBootSettings Error, ${bootConfig.message}")
            }

            is UiState.Loading -> {}
        }
        Timber.d("BootViewModel save finish")
        return bootConfig
    }


    fun saveBootConfigurations(configs: BootConfigurations?) {
        bootUtil.saveBootConfigurations(configs)
    }

    fun saveServerList(serverList: List<RespBootSettingInfo.ServerInfo>) {
        bootUtil.saveServerList(serverList)
    }

    fun loadBootConfigurations(): BootConfigurations? {
        return bootUtil.loadBootConfigurations()
    }

    fun loadServerList(): List<RespBootSettingInfo.ServerInfo>? {
        return bootUtil.loadServerList()
    }
}
