package com.skb.mytvlibrary.navigator

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.skb.btvplus.navigator.LandingViewModel
import com.skb.btvplus.presenter.screen.detail.DetailLandingItem
import com.skb.btvplus.presenter.screen.detail.DetailScreen
import com.skb.btvplus.presenter.screen.home.HomeLandingItem
import com.skb.btvplus.presenter.screen.home.HomeScreen

const val TAG = "NavigationHost"

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: Screens,
) {
    NavHost(navController, startDestination = startDestination.route) {
        composable(Screens.Home.route) {
            HomeScreen(
                landingViewModel = hiltViewModel<LandingViewModel>().apply {
                    updateLandingItem(HomeLandingItem(id = "init home"))
                },
                navController = navController
            )
        }
        composable(Screens.Detail.route) {
            DetailScreen(
                landingViewModel = hiltViewModel<LandingViewModel>().apply {
                    updateLandingItem(DetailLandingItem(id = "init detail"))
                },
                navController = navController
            )
        }
    }
}