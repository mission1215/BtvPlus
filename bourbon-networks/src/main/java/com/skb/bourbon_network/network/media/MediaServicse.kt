package com.skb.bourbon_network.network.media

import com.skb.bourbon_network.extentsions.getHost

class MediaServicse {
    companion object {
        private const val TAG = "BootService"

        private const val PRODUCTION_URL = "https://agw.sk-iptv.com:8087/"
        private const val STAGING_URL = "https://agw-stg.sk-iptv.com:8087/"

        fun getBaseUrl(isStaging: Boolean) = if (isStaging) STAGING_URL else PRODUCTION_URL

        fun getTrustedHosts(isStaging: Boolean): List<String?> {
            return listOf(getBaseUrl(isStaging).getHost())
        }
    }

    // SCS 연동 필요
}