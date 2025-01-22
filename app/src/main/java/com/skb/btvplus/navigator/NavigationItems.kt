package com.skb.mytvlibrary.navigator

import android.net.Uri
import com.skb.btvplus.extensions.toJson
import com.skb.btvplus.main.BaseNavItem
import com.skb.btvplus.presenter.screen.detail.DetailNavItem

sealed class Screens(val route: String) {

    object Home : Screens("home?landingItem={landingItem}") {
        fun route(navItem: BaseNavItem): String {
            return "home?landingItem=${Uri.encode(navItem.toJson())}"
        }
    }

    object Detail : Screens("detail?landingItem={landingItem}") {
        fun route(navItem: DetailNavItem): String {
            return "detail?landingItem=${Uri.encode(navItem.toJson())}"
        }
    }
}
