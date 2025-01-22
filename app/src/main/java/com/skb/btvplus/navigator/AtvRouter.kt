package com.skb.btvplus.navigator

import androidx.navigation.NavHostController
import com.skb.btvplus.presenter.screen.detail.DetailNavItem
import com.skb.mytvlibrary.navigator.Screens

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
fun NavHostController.navigateToHome(detailNavItem: DetailNavItem) {
    navigate(Screens.Detail.route(detailNavItem)) {
        launchSingleTop = true
        restoreState = true
    }
}

/**
 * Navigate to detail
 *
 * @param detailNavItem
 */
fun NavHostController.navigateToDetail(detailNavItem: DetailNavItem) {
    navigate(Screens.Detail.route(detailNavItem)) {
        launchSingleTop = true
        restoreState = true
    }
}



