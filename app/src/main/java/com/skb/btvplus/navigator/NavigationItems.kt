package com.skb.mytvlibrary.navigator

import android.net.Uri
import com.skb.btvplus.extensions.toJson
import com.skb.btvplus.main.BaseNavItems

sealed class Screens(val route: String) {

    object Home : Screens("home?landingItem={landingItem}") {
        fun route(navItem: BaseNavItems): String {
            return "home?landingItem=${Uri.encode(navItem.toJson())}"
        }
    }

    object Detail : Screens("detail?landingItem={landingItem}") {
        fun route(navItem: BaseNavItems): String {
            return "detail?landingItem=${Uri.encode(navItem.toJson())}"
        }
    }
}
