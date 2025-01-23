package com.skb.bourbondomainlib.network.carbon

/**
 * ${A.TV}
 * Created by SK_BroadBand
 * Date: 2023/01/27
 * Description:
 */
data class ShelfInfoResDto(
    val shelfId: String?, // default: SHLF_101
    val shelfName: String?, // default: 전체 선반이름 (크림버전 상단 탭)
    //val raceCallId: String?, // default: 해당 탭이 추천탭이라면 값이 내려가며, 해당 탭은 아래 id를 인자로 race api를 호출해야 합니다. RACE ID
    val sort: String?,
    val timestamp: String?,
    val cardTypes : List<String>
)
