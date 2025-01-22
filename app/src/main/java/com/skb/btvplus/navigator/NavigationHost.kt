package com.skb.mytvlibrary.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.skb.btvplus.extensions.fromJson
import com.skb.btvplus.main.BaseNavItem
import com.skb.btvplus.presenter.screen.detail.DetailNavItem
import com.skb.btvplus.presenter.screen.detail.DetailScreen
import com.skb.btvplus.presenter.screen.home.HomeScreen

const val TAG = "NavigationHost"
const val ARG_NAV_ITEM = "navItem"

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: Screens,
    initialNavItem: BaseNavItem, // 초기 LandingItem 추가
) {
    NavHost(navController, startDestination = startDestination.route) {
        composable(
            route = Screens.Home.route,
            arguments = listOf(
                navArgument("landingItem") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // 초기 LandingItem 또는 네비게이션 경로에서 전달받은 LandingItem 사용
            val landingItemJson = backStackEntry.arguments?.getString(ARG_NAV_ITEM)
            val landingItem = landingItemJson?.let { it.fromJson() } ?: initialNavItem

            HomeScreen(
                navItem = landingItem,
                navController = navController
            )
        }

        composable(
            route = Screens.Detail.route,
            arguments = listOf(
                navArgument("landingItem") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val landingItemJson = backStackEntry.arguments?.getString(ARG_NAV_ITEM)
            val landingItem = landingItemJson?.let { it.fromJson() } ?: DetailNavItem()

            DetailScreen(
                landingItem = landingItem,
                navController = navController
            )
        }
    }
}



