package com.skb.bourbondomainlib.repository

import com.skb.bourbondomainlib.network.CommonHeader
import com.skb.bourbondomainlib.network.CommonRequest
import com.skb.bourbondomainlib.network.UiState
import com.skb.bourbondomainlib.network.carbon.CarbonService
import com.skb.bourbondomainlib.network.carbon.ResponseListShelfInfoResDto
import com.skb.bourbondomainlib.network.carbon.ResponseListShelfResDto
import com.skb.bourbondomainlib.network.executeApiCallSync
import javax.inject.Inject

/**
 * Home api repository
 *
 * @property carbonService
 * @property bootService
 * @constructor Create empty Home api repository
 */
class HomeApiRepository @Inject constructor(
    private val carbonService: CarbonService,
) {
    /**
     * Request get shelf info
     * 탭 정보
     * @return
     */
    suspend fun requestGetShelfInfo(): UiState<ResponseListShelfInfoResDto> {
        val header = CommonHeader(CommonHeader.HeaderType.Carbon).mBaseMap
        val request = CommonRequest().apply {
            add("pocType", "Adot_Trend")
        }.mBaseMap
        return executeApiCallSync {
            carbonService.requestGetShelfInfo(
                reqHeaderMap = header, reqQueryMap = request
            )
        }
    }

    /**
     * Request get external shelf
     * 홈 선반 정보
     *
     * @param tabId
     * @return
     */

    suspend fun requestGetExternalShelf(tabId: String): UiState<ResponseListShelfResDto> {
        val header = CommonHeader(CommonHeader.HeaderType.Carbon).mBaseMap
        val request = CommonRequest().apply {
            add("pocType", "Adot_Trend")
        }.mBaseMap
        return executeApiCallSync { carbonService.requestGetExternalShelf(
            reqHeaderMap = header,
            id = tabId,
            reqQueryMap = request
        ) }
    }


}