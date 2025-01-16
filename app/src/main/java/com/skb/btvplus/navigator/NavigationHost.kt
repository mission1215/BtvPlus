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
    NavHost(navController, startDestination = startDestination.route, enterTransition = {
        slideInVertically(
            initialOffsetY = { it },  // Slide in from the bottom
            animationSpec = tween(durationMillis = 300)
        ) + fadeIn(
            animationSpec = tween(durationMillis = 300)
        )
    }, exitTransition = {
        // No slide out transition, just fade out
        fadeOut(
            animationSpec = tween(durationMillis = 300)
        )
    }, popEnterTransition = {
        // No slide in transition for popping back, just fade in
        fadeIn(
            animationSpec = tween(durationMillis = 300)
        )
    }, popExitTransition = {
        slideOutVertically(
            targetOffsetY = { it },  // Slide out to the bottom
            animationSpec = tween(durationMillis = 300)
        ) + fadeOut(
            animationSpec = tween(durationMillis = 300)
        )
    }) {
        composable(Screens.Home.route) { HomeScreen(sharedViewModel, navController) }
        composable(Screens.Detail.route) { DetailScreen(sharedViewModel, navController) }
    }
}