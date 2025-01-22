package com.skb.btvplus.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.skb.btvplus.extensions.fromJson
import com.skb.btvplus.main.BaseNavItems
import com.skb.btvplus.presenter.screen.detail.DetailScreen
import com.skb.btvplus.presenter.screen.home.HomeScreen

const val TAG = "NavigationHost"
const val ARG_NAV_ITEM = "navItem"

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: Screens,
    initialNavItem: BaseNavItems, // 초기 LandingItem 추가
) {
    NavHost(navController, startDestination = startDestination.route) {
        composable(
            route = Screens.Home.route,
            arguments = listOf(
                navArgument("landingItem") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // 네비게이션 경로에서 전달받은 LandingItem 또는 초기값 사용
            val navItemJson = backStackEntry.arguments?.getString(ARG_NAV_ITEM)
            val navItem = navItemJson?.let { it.fromJson<BaseNavItems>() } ?: initialNavItem
            HomeScreen(
                navItem = navItem, navController = navController
            )
        }

        composable(
            route = Screens.Detail.route,
            arguments = listOf(
                navArgument("landingItem") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val navItemJson = backStackEntry.arguments?.getString(ARG_NAV_ITEM)
            val navItem = navItemJson?.let { it.fromJson<BaseNavItems>() } ?: initialNavItem

            DetailScreen(
                navItem = navItem, navController = navController
            )
        }
    }
}



