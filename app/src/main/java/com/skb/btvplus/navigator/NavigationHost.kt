package com.skb.mytvlibrary.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.skb.btvplus.extensions.fromJson
import com.skb.btvplus.navigator.LandingItem
import com.skb.btvplus.presenter.screen.detail.DetailScreen
import com.skb.btvplus.presenter.screen.home.HomeScreen

const val TAG = "NavigationHost"

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: Screens,
    initialLandingItem: LandingItem // 초기 LandingItem 추가
) {
    NavHost(navController, startDestination = startDestination.route) {
        composable(
            route = Screens.Home.route,
            arguments = listOf(
                navArgument("landingItem") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // 초기 LandingItem 또는 네비게이션 경로에서 전달받은 LandingItem 사용
            val landingItemJson = backStackEntry.arguments?.getString("landingItem")
            val landingItem = landingItemJson?.let { it.fromJson() } ?: initialLandingItem

            HomeScreen(
                landingItem = landingItem,
                navController = navController
            )
        }

        composable(
            route = Screens.Detail.route,
            arguments = listOf(
                navArgument("landingItem") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val landingItemJson = backStackEntry.arguments?.getString("landingItem")
            val landingItem = landingItemJson?.let { it.fromJson() } ?: initialLandingItem

            DetailScreen(
                landingItem = landingItem,
                navController = navController
            )
        }
    }
}



