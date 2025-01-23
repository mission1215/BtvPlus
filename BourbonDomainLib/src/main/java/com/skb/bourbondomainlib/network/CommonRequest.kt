package com.skb.bourbondomainlib.network

/**
 * Common request
 *
 * @constructor Create empty Common request
 */
class CommonRequest : BaseMap {
    companion object {
        const val IF = "IF"
        const val response_format = "response_format"
        const val stb_id = "stb_id"
        const val menu_id = "menu_id"
        const val cw_call_id = "cw_call_id"
        const val type = "type"
        const val seg_id = "seg_id"
        const val inspect_yn = "inspect_yn"
        const val app_typ_cd = "app_typ_cd"
        const val menu_stb_svc_id = "menu_stb_svc_id"
        const val version = "version"
        const val page_no = "page_no"
        const val page_cnt = "page_cnt"
        const val sort_typ_cd = "sort_typ_cd"
        const val search_type = "search_type"
        const val epsd_id = "epsd_id"
        const val epsd_rslu_id = "epsd_rslu_id"
        const val ver = "ver"
        const val ui_name = "ui_name"
        const val method = "method"
        const val devicetype = "devicetype"
        const val useragent = "useragent"
        const val cid = "cid"
        const val verf_req_data = "verf_req_data"
        const val mac_exclude_check_yn = "mac_exclude_check_yn"
        const val mac_address = "mac_address"
        const val swVersion = "swVersion"
        const val use_subtitle_yn = "use_subtitle_yn"
        const val m_drm = "m_drm"
        const val mbtv_key = "mbtv_key"
        const val device_hdcp = "device_hdcp"
        const val device_security_level = "device_security_level"
        const val req_date = "req_date"
        const val pre_flag = "pre_flag"
        const val eag_protocol = "eag_protocol"
        const val m_useragent = "m_useragent"
        const val target_system = "target_system"
        const val track_id = "track_id"
        const val session_id = "session_id"
        const val poc_typ_cd = "poc_typ_cd"
        const val channels = "channels"
        const val startTime = "event_fr_dt"
        const val endTime = "event_to_dt"
        const val gender = "gender"
        const val age = "age"
        const val ApiKey = "X-API-KEY"
        const val media_typ_cd = "media_typ_cd"
        const val legal_age = "legalAge"                            //Coral ver. 사용자 생년월일 필드 추가
    }

    constructor()
}