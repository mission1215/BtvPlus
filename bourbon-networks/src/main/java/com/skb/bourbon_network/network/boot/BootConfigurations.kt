package com.skb.mytvlibrary.server.service.heb

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BootConfigurations(
    /** 편성표 refresh polling 주기 (단위 : 분) */
    @SerializedName("epg_cache_time") var epgCacheTime: Int = 120,
    /** 한 번 편성표 요청할 때의 분량 (단위 : 시) */
    @SerializedName("epg_fetch_duration") var epgFetchDuration: Int = 24,
    /** 마이 채널 구성 프로그램 조회용 CW CALL ID */
    @SerializedName("my_channel_cw_call_id") var myChannelCwCallId: String? = "MY_CHANNEL.RACE",
    /** 프로모션 채널 구성 프로그램 조회용 CW CALL ID */
    @SerializedName("promotion_channel_cw_call_id") var promotionChannelCwCallId: String? = "PROMOTION.RACE",
    /** 프로모션 채널 ID ((HEB Channel 로 운영 시 사용) */
    @SerializedName("promotion_channel_id") var promotionChannelId: String? = "C0150", // TODO : 변경 될 예정
    /** 인트로 애니메이션 WebP 경로(1페이지) */
    @SerializedName("intro_anim_1") var introAnimation1: String? = "https://cdn.sktapollo.com/developers/poc/app.apollo.agent/static/mytv/intro_1.webp",
    /** 인트로 애니메이션 WebP 경로(2페이지) */
    @SerializedName("intro_anim_2") var introAnimation2: String? = "https://cdn.sktapollo.com/developers/poc/app.apollo.agent/static/mytv/intro_2.webp",
    /** 인트로 애니메이션 WebP 경로(3페이지) */
    @SerializedName("intro_anim_3") var introAnimation3: String? = "https://cdn.sktapollo.com/developers/poc/app.apollo.agent/static/mytv/intro_3.webp",
    /** 다운로드 설정 관련 object */
    @SerializedName("download") var download: DownloadConfigurations? = DownloadConfigurations(),
    /** AI PLAY MODE Progress Time (단위: 초) */
    @SerializedName("ai_play_delay") var aiPlayDelay: Int? = 3,
    /** 상세화면 리스트 max 사이즈 */
    @SerializedName("detail_list_max_size") var detailListMaxSize: Int = 20,
    /** 프로야구 관련 설정 **/
    @SerializedName("baseball_configuration") var kboConfiguration: KBOConfiguration? = KBOConfiguration(),
    /** Live Channel streaming 주소 복호화 키 **/
    @SerializedName("live_channel_url_decryption_key") var liveChannelUrlDecriptionKey: String? = ""
) : Serializable

data class KBOConfiguration(
    /** Baseball 야구 안내 메시지 노출 시간 (단위: 초) */
    @SerializedName("baseball_popup_guide_exposure_time") var kboPopupGuideExposureTime: Int = 2,
    /** Baseball 야구 안내 메시지 타이틀 */
    @SerializedName("baseball_popup_guide_title") var kboPopupGuideTitle: String? = "프로야구 경기가 진행 중이에요!",
    /** Baseball 야구 안내 메시지 설명(타이틀 아래 메시지) */
    @SerializedName("baseball_popup_guide_description") var kboPopupGuideDescription: String? = "지금 보러가실래요?",
    /** Baseball 스퀴즈런 버튼 노출 Y/N) */
    @SerializedName("baseball_squiz_run_exposure") var kboSquizRunExposure: String? = "Y",
) : Serializable

data class DownloadConfigurations(
    /** 다운로드 설정 on/off default 값 */
    @SerializedName("enabled") var isEnabled: Boolean = false,
    /** 다운로드 파일 최대 보관 개수 */
    @SerializedName("count_max") var countMax: Int? = 30,
    /** 다운로드 가용 용량 설정 가능 최소값 (단위 : GB) */
    @SerializedName("capacity_min") var capacityMin: Int = 5,
    /** 다운로드 가용 용량 설정 가능 최대값 (단위 : GB) */
    @SerializedName("capacity_max") var capacityMax: Int = 50,
    /** 다운로드 가용 용량 설정 default 값 (단위 : GB) */
    @SerializedName("capacity_default") var capacityDefault: Int = 10,
    /** 다운로드 용량 설정 +/- 할 때의 변경 값 (단위 : GB) */
    @SerializedName("capacity_adjust_unit") var capacityAdjustUnit: Int? = 1,
    /** 자동 다운로드 시작 시간 (최한시 시작, 예 - "02:00") */
    @SerializedName("auto_dl_start_time") var autoDownloadStartTimeString: String? = "02:00",
    /** 자동 다운로드 종료 시간 (최한시 종료, 예 - "06:00") */
    @SerializedName("auto_dl_end_time") var autoDownloadEndTimeString: String? = "06:00",
    /** 자동 다운로드 Slot duration (단위 : 초) */
    @SerializedName("auto_dl_slot_duration") var autoDownloadSlotDuration: Int? = null,
    /**
     * 자동 다운로드 네트워크 상태 조건
     * wifi : Wi-FI 접속 상태일 때만 다운로드
     * cellular: 셀룰러 접속 상태일 때만 다운로드
     * all : 온라인이면 다운로드
     */
    @SerializedName("auto_dl_condition_network") var autoDownloadConditionNetwork: String = NETWORK_CONDITION_ALL,
    /** 비 충전 상태일 때, 자동 다운로드 배터리 상태 조건 (단위 : 백분율, 0이면 충전 상태 고려 안하고 다운로드 실시) */
    @SerializedName("auto_dl_condition_battery") var autoDownloadConditionBattery: Int? = 45,
    /** 충전 상태일 때, 자동 다운로드 배터리 상태 조건 (단위 : 백분율, 0이면 충전 상태 고려 안하고 다운로드 실시) */
    @SerializedName("auto_dl_condition_battery_on_charging") var autoDownloadConditionBatteryOnCharging: Int? = 35,
    /** 일 당 자동 다운로드 개수 */
    @SerializedName("auto_dl_count_per_day") var autoDownloadCountPerDay: Int? = 2,
    /** 자동 다운로드 목록 획득을 위한 EUXP-009 호출 파라미터 */
    @SerializedName("auto_dl_list_cw_call_id") var autoDownloadListCwCallId: String? = "PRE_DOWNLOAD.RACE",
    /** 자동 다운로드 실패 시 재 시도 횟수 */
    @SerializedName("auto_dl_retry_count") var autoDownloadRetryCount: Int = 0,
    /**
     * 자동 다운로드 파일 유지 기간 (단위 : hour)
     * 본래 DRM packaged media file 의 license 유효기간으로 파일을 삭제 하는 것이 원칙이나,
     * 본 설정 값이 0보다 크면, 해당 유효기간을 본 설정 값으로 강제 치환하여 삭제 로직을 수행한다.
     */
    @SerializedName("auto_dl_force_expire_time") var autoDownloadForceExpireTime: Int = 0,
    /**
     * 수동 다운로드 파일 유지 기간 (단위 : hour)
     * 본래 DRM packaged media file 의 license 유효기간으로 파일을 삭제 하는 것이 원칙이나,
     * 본 설정 값이 0보다 크면, 해당 유효기간을 본 설정 값으로 강제 치환하여 삭제 로직을 수행한다.
     */
    @SerializedName("dl_force_expire_time") var downloadForceExpireTime: Int = 0,
    /**
     * 다운로드 설정 on 유도 조건 1
     * 0 이면 동작 안 함.
     * (채널 재핑 횟수)
     */
    @SerializedName("dl_suggest_condition_1") var downloadSuggestCondition1: Int = 30,
    /**
     * 다운로드 설정 on 유도 조건 2
     * 0 이면 동작 안 함.
     * (활용 방안 미정)
     */
    @SerializedName("dl_suggest_condition_2") var downloadSuggestCondition2: Int? = 0,
    /** 일 당 수동 다운로드 개수(접속망 관계 없음)
     * 10000이면 "무제한"
     * 0이면 이 조건 무시
     */
    @SerializedName("dl_count_per_day_on_all") var downloadCountPerDayOnAll: Int? = 0,
    /** 일 당 수동 다운로드 개수(wifi 접속 시) */
    @SerializedName("dl_count_per_day_on_wifi") var downloadCountPerDayOnWifi: Int? = 10000,
    /** 일 당 수동 다운로드 개수(cellular 접속 시) */
    @SerializedName("dl_count_per_day_on_cellular") var downloadCountPerDayOnCellular: Int? = 5,
    /** 수동 다운로드 대상 해상도 (FHD/HD/SD) */
    @SerializedName("dl_target_resolution") var downloadTargetResolution: String = RESOLUTION_FHD,
    /** 자동 다운로드 대상 해상도 (FHD/HD/SD) */
    @SerializedName("auto_dl_target_resolution") var autoDownloadTargetResolution: String = RESOLUTION_FHD,
    /** partial download 분량 (단위 : 초) */
    @SerializedName("pdl_duration") var partialDownloadDuration: Int? = null,
    /** predl startdownload delay (단위 : ms) */
    @SerializedName("pdl_download_delay") var predlStartDownloadDelay: Int? = 3000,
    /** 만료 '예정' 안내 표시일 (라이선스 만료일 기준 -n 일) */
    @SerializedName("expire_inform_days") var expireInformDays: Int? = 3,
    /** 만료 '임박' 안내 표시일 (라이선스 만료일 기준 -n 일) */
    @SerializedName("expire_approaching_days") var expireApproachingDays: Int? = 1,
    /** 자동 다운로드 알람이 지연되었을 경우, 자동 다운로드를 진행 할지 말지의 설정. true : 다운로드 진행, false : 다운로드 미 진행 */
    @SerializedName("deferred_alarm") var deferredAlarm: Boolean? = false,
    /** 파일 삭제 이후 local DB 상에 남겨두는 기간. 삭제된 이후에도 재 다운로드가 가능하게 하기 위함(단위 : 일) */
    @SerializedName("keeping_days_after_delete") var keepingDaysAfterDelete: Int? = 30
) : Serializable {
    companion object {
        const val NETWORK_CONDITION_WIFI = "wifi"
        const val NETWORK_CONDITION_CELLULAR = "cellular"
        const val NETWORK_CONDITION_ALL = "all"
        const val RESOLUTION_FHD = "FHD"
        const val RESOLUTION_HD = "HD"
        const val RESOLUTION_SD = "SD"
    }
}