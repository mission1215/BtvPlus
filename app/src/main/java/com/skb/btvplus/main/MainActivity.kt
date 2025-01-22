package com.skb.btvplus.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.skb.btvdomainlib.network.UiState
import com.skb.btvplus.main.BaseNavItems
import com.skb.btvplus.ui.theme.BtvPlusTheme
import com.skb.btvplus.utils.LocalSharedViewModel
import com.skb.mytvlibrary.navigator.NavigationHost
import com.skb.mytvlibrary.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.apply

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BtvPlusTheme {
                val bootViewModel = hiltViewModel<BootViewModel>()
                val bootConfig = bootViewModel.bootConfig.collectAsStateWithLifecycle().value
                val rememberNavController = rememberNavController()
                val sharedViewModel = remember { SharedViewModel() }
                CompositionLocalProvider(
                    LocalSharedViewModel provides sharedViewModel
                ) {
                    LaunchedEffect(key1 = bootViewModel) {
                        bootViewModel.navigationEvent.collect {

                        }
                    }
                    when (bootConfig) {
                        is UiState.Loading -> {
                            // Show loading indicator
                            Timber.d("UiState.Loading")
                        }

                        is UiState.Success -> {
                            Timber.d("UiState.Success, ${bootConfig.data}")
                            NavigationHost(navController = rememberNavController,
                                startDestination = Screens.Home,
                                initialNavItem = BaseNavItems.HomeNavItem(id = "Home"))
                        }

                        is UiState.Error -> {
                            Timber.d("UiState.Error, ${bootConfig.message} ")
                            // Show error message
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BtvPlusTheme {

    }
}