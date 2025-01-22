package com.skb.mytvlibrary.navigator

import android.net.Uri
import com.skb.btvplus.extensions.toJson
import com.skb.btvplus.navigator.LandingItem

sealed class Screens(val route: String) {

    object Home : Screens("home?landingItem={landingItem}") {
        fun route(landingItem: LandingItem): String {
            return "home?landingItem=${Uri.encode(landingItem.toJson())}"
        }
    }

    object Detail : Screens("detail?landingItem={landingItem}") {
        fun route(landingItem: LandingItem): String {
            return "detail?landingItem=${Uri.encode(landingItem.toJson())}"
        }
    }
}
