package com.skb.bourbon.navigator

import android.net.Uri
import com.skb.bourbon.extensions.toJson
import com.skb.bourbon.main.BaseNavItems

sealed class Screens(val route: String) {

    object Home : Screens("home?navItem={navItem}") {
        fun route(navItem: BaseNavItems): String {
            return "home?navItem=${Uri.encode(navItem.toJson())}"
        }
    }

    object Detail : Screens("detail?navItem={navItem}") { // Updated to match "navItem"
        fun route(navItem: BaseNavItems): String {
            return "detail?navItem=${Uri.encode(navItem.toJson())}"
        }
    }
}
