package com.skb.btvplus.navigator

import com.google.gson.Gson

data class LandingItem(
    var id: String? = null,
    val name: String? = null
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): LandingItem {
            return Gson().fromJson(json, LandingItem::class.java)
        }
    }
}
