package com.skb.bourbon.navigator

import android.net.Uri
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class Screens(val route: String) {
    data class Home(val navItem: BaseNavItems.HomeNavItem? = null) : Screens("home") {
        fun route(): String {
            return if (navItem != null) {
                "home?navItem=${Uri.encode(Json.encodeToString(navItem))}"
            } else {
                "home"
            }
        }
    }

    data class Detail(val navItem: BaseNavItems.DetailNavItem) : Screens("detail") {
        fun route(): String {
            return "detail?navItem=${Uri.encode(Json.encodeToString(navItem))}"
        }
    }
}
