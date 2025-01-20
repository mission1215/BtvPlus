package com.skb.mytvlibrary.navigator

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.skb.btvplus.navigator.SharedViewModel
import com.skb.btvplus.presenter.screen.detail.DetailScreen
import com.skb.btvplus.presenter.screen.home.HomeScreen

const val TAG = "NavigationHost"

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: Screens,
    sharedViewModel: SharedViewModel,
) {
    NavHost(navController, startDestination = startDestination.route) {
        composable(Screens.Home.route) {
            HomeScreen(
                sharedViewModel = sharedViewModel,
                navController = navController
            )
        }
        composable(Screens.Detail.route) {
            DetailScreen(
                sharedViewModel = sharedViewModel,
                navController = navController
            )
        }
    }
}