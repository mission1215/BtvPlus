package com.skb.bourbon.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.skb.bourbon.main.BaseNavItems
import com.skb.bourbon.presenter.screen.detail.DetailScreen
import com.skb.bourbon.presenter.screen.home.HomeScreen
import kotlinx.serialization.json.Json

const val TAG = "NavigationHost"
const val ARG_NAV_ITEM = "navItem"

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: Screens,
    initialNavItem: BaseNavItems, // Initial navigation item
) {
    NavHost(navController, startDestination = startDestination.route) {
        // Home screen composable
        composable(
            route = "home?navItem={navItem}",
            arguments = listOf(
                navArgument(ARG_NAV_ITEM) { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val navItemJson = backStackEntry.arguments?.getString(ARG_NAV_ITEM)
            val navItem = navItemJson?.let { Json.decodeFromString<BaseNavItems.HomeNavItem>(it) }
                ?: initialNavItem as BaseNavItems.HomeNavItem
            HomeScreen(navItem = navItem, navController = navController)
        }

        // Detail screen composable
        composable(
            route = "detail?navItem={navItem}",
            arguments = listOf(
                navArgument(ARG_NAV_ITEM) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val navItemJson = backStackEntry.arguments?.getString(ARG_NAV_ITEM)
            val navItem = Json.decodeFromString<BaseNavItems.DetailNavItem>(navItemJson ?: "")
            DetailScreen(navItem = navItem, navController = navController)
        }
    }
}





