package com.skb.bourbon.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.skb.bourbon.presenter.screen.home.HomeViewModel
import com.skb.bourbon.navigator.navigateToDetail
import timber.log.Timber

/**
 * Navigation event
 *
 * @constructor Create empty Navigation event
 */
sealed class NavigationEvent {
    class NavigateToHome(navItem: BaseNavItems? = null) : NavigationEvent()
    class NavigateToDetail(navItem: BaseNavItems? = null) : NavigationEvent()
}

@Composable
fun HandleNavigationEvents(homeViewModel: HomeViewModel, navController: NavHostController) {
    val navigationEvent = homeViewModel.navigationEventState.collectAsStateWithLifecycle(null).value
    Timber.d("EventListener $navigationEvent")
    when (navigationEvent) {
        is NavigationEvent.NavigateToDetail -> {
            navController.navigateToDetail(BaseNavItems.DetailNavItem(id = "Detail"))
        }

        null -> {
            Timber.d("EventListener null")
        }

        else -> {}
    }
}