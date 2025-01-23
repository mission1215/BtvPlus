package com.skb.bourbon.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.skb.bourbon.extensions.fromJson
import com.skb.bourbon.main.BaseNavItems
import com.skb.bourbon.presenter.screen.detail.DetailScreen
import com.skb.bourbon.presenter.screen.home.HomeScreen

const val TAG = "NavigationHost"
const val ARG_NAV_ITEM = "navItem"

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: Screens,
    initialNavItem: BaseNavItems, // Initial Landing Item
) {
    NavHost(navController, startDestination = startDestination.route) {
        composable(
            route = Screens.Home.route,
            arguments = listOf(
                navArgument(ARG_NAV_ITEM) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val navItemJson = backStackEntry.arguments?.getString(ARG_NAV_ITEM)
            val navItem =
                navItemJson?.let { it.fromJson<BaseNavItems.HomeNavItem>() } ?: initialNavItem
            HomeScreen(
                navItem = navItem as BaseNavItems.HomeNavItem, navController = navController
            )
        }

        composable(
            route = Screens.Detail.route, // Matches updated route definition
            arguments = listOf(
                navArgument(ARG_NAV_ITEM) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val navItemJson = backStackEntry.arguments?.getString(ARG_NAV_ITEM)
            val navItem =
                navItemJson?.let { it.fromJson<BaseNavItems.DetailNavItem>() } ?: initialNavItem

            when (navItem) {
                is BaseNavItems.DetailNavItem -> {
                    DetailScreen(
                        navItem = navItem,
                        navController = navController
                    )
                }

                else -> {}
            }
        }
    }
}




