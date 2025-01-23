package com.skb.bourbon.navigator

import androidx.navigation.NavHostController

/**
 * Navigate to home
 *
 * @param detailNavItem
 */
fun NavHostController.navigateToHome(navItem: BaseNavItems.HomeNavItem) {
    navigate(Screens.Home(navItem).route()) {
        launchSingleTop = true
        restoreState = true
    }
}

/**
 * Navigate to detail
 *
 * @param detailNavItem
 */
fun NavHostController.navigateToDetail(navItem: BaseNavItems.DetailNavItem) {
    navigate(Screens.Detail(navItem).route()) {
        launchSingleTop = true
        restoreState = true
    }
}



