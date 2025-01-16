package com.skb.btvdomainlib.network

import java.text.SimpleDateFormat
import java.util.*

/**
 * Common header
 *
 * @constructor Create empty Common header
 */
class CommonHeader : BaseMap {
    companion object {
        private const val API_KEY = "I7xx6e7b667188364c329cac10f97e38a238"

        /** Configration Api*/
        private const val CONFIG_API_KEY_PRD = "YZx77pfdLr68WvfYBeXQLZksiFyOqbH9OcwGO0j0"
        private const val CONFIG_API_KEY_STG = "cKSm3pbDAV7rWyMF9dQgYlkFpz27vrad3ysER6g0"

        /** Carbon Api*/
        private const val CARBON_API_KEY_PRD =
            "I7xx6e7b667188364c329cac10f97e38a238"//"l7xx851d12cc66dc4d2e86a461fb5a530f4a"  카본 공용 key 로 변경
        private const val CARBON_API_KEY_STG =
            "I7xx6e7b667188364c329cac10f97e38a238"//"l7xx159a8ca72966400b886a93895ec9e2e3"

        const val VERSION = "1.0"
        const val TRACE = "MyTV^APIGW"

        const val contentsType = "Content-Type"
        const val accept = "Accept"
        const val clientId = "Client_ID"
        const val clientIP = "Client_IP"
        const val timestamp = "TimeStamp"
        const val authVal = "Auth_Val"
        const val auth = "Auth"
        const val token = "Token"
        const val connection = "Connection"
        const val apiKey = "Api_Key"
        const val trace = "Trace"
        const val myTvAuthKey = "mytv_auth_key"
    }

    constructor(stg: Boolean) {
        val key = if (stg) CARBON_API_KEY_STG else CARBON_API_KEY_PRD
        val format = SimpleDateFormat("yyyyMMddHHmmss.SSS")
        val date = Date()
        add("accept", "application/json;charset=utf-8")
        add(contentsType, "application/json;charset=utf-8")
        add(accept, "application/json;charset=utf-8")
        add(timestamp, format.format(date))
        add(apiKey, key)
        add(trace, "Adot")
        add(connection, "close")
    }
}