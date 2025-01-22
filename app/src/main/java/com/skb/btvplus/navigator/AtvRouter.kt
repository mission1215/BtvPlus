package com.skb.mytvlibrary.navigator

import androidx.navigation.NavHostController
import com.skb.btvplus.navigator.LandingItem
import timber.log.Timber

sealed class LandingViewType(){
    object Home : LandingViewType()
    object Detail : LandingViewType()
}
/**
 * Navigation host view >  엡 내부에서 Navigation시 사용
 *
 * @param navController
 * @param landingViewModel
 * @param router
 * @param landingItem
 */
fun navigationHostView(
    navController: NavHostController,
    ladingViewType: LandingViewType,
    landingItem: LandingItem,
) {
    when (ladingViewType) {
        LandingViewType.Home -> {
            navController.navigate(Screens.Home.route(landingItem)) {
                launchSingleTop = true
                restoreState = true
            }
        }

        LandingViewType.Detail  -> {
            landingItem.let {
                navController.navigate(Screens.Detail.route(landingItem)) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}

