package com.skb.mytvlibrary.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.skb.btvplus.navigator.LandingViewModel
import com.skb.btvplus.presenter.screen.detail.DetailScreen
import com.skb.btvplus.presenter.screen.home.HomeScreen

const val TAG = "NavigationHost"

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: Screens,
    landingViewModel: LandingViewModel,
) {
    NavHost(navController, startDestination = startDestination.route) {
        composable(Screens.Home.route) {
            HomeScreen(
                landingViewModel = landingViewModel,
                navController = navController
            )
        }
        composable(Screens.Detail.route) {
            DetailScreen(
                landingViewModel = landingViewModel,
                navController = navController
            )
        }
    }
}