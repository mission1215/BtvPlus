package com.skb.bourbon.navigator

import androidx.navigation.NavHostController
import com.skb.bourbon.main.BaseNavItems

/**
 * Landing view type
 *
 * @constructor Create empty Landing view type
 */
sealed class LandingViewType(){
    object Home : LandingViewType()
    object Detail : LandingViewType()
}

/**
 * Navigate to home
 *
 * @param detailNavItem
 */
fun NavHostController.navigateToHome(navItem: BaseNavItems) {
    navigate(Screens.Home.route(navItem)) {
        launchSingleTop = true
        restoreState = true
    }
}

/**
 * Navigate to detail
 *
 * @param detailNavItem
 */
fun NavHostController.navigateToDetail(navItem: BaseNavItems) {
    navigate(Screens.Detail.route(navItem)) {
        launchSingleTop = true
        restoreState = true
    }
}



