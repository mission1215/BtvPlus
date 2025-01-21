package com.skb.mytvlibrary.navigator

import androidx.navigation.NavHostController
import com.skb.btvplus.navigator.LandingItem
import com.skb.btvplus.navigator.LandingViewModel

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
    landingViewModel: LandingViewModel,
    router: String,
    landingItem: LandingItem,
) {
    if (landingItem != null) {
        landingViewModel.landingItem = landingItem
    }

    when (router) {
        Screens.Home.route -> {
            navController.navigate(Screens.Home.route) {
                launchSingleTop = true
                restoreState = true
            }
        }

        Screens.Detail.route -> {
            landingItem.let {
                navController.navigate(Screens.Detail.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}

