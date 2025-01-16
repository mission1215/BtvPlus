package com.skb.btvplus.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.skb.btvplus.navigator.LandingItem
import com.skb.btvplus.navigator.SharedViewModel
import com.skb.btvplus.ui.theme.BtvPlusTheme
import com.skb.mytvlibrary.navigator.NavigationHost
import com.skb.mytvlibrary.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BtvPlusTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    val bootViewModel = hiltViewModel<BootViewModel>()
//                    val bootConfig = bootViewModel.bootConfig.collectAsStateWithLifecycle().value
                    val rememberNavController = rememberNavController()
                    val sharedViewModel = hiltViewModel<SharedViewModel>()
                    sharedViewModel.landingItem = LandingItem()

                    Surface(
                        modifier = Modifier
                            .padding(innerPadding)
                            .wrapContentSize(align = Alignment.Center)
                    ) {
                        NavigationHost(rememberNavController, Screens.Home, sharedViewModel)
                    }
//                    LaunchedEffect(key1 = bootViewModel) {
//                        bootViewModel.navigationEvent.collect {
//                            rememberNavController.navigate(Screens.Home.route)
//                        }
//                    }
//
//                    Surface(modifier = Modifier.padding(innerPadding)) {
//                        when (bootConfig) {
//                            is UiState.Loading -> {
//                                // Show loading indicator
//                            }
//
//                            is UiState.Success -> {
//                                NavigationHost(rememberNavController, Screens.Home, sharedViewModel)
//                            }
//
//                            is UiState.Error -> {
//                                // Show error message
//                            }
//                        }
//                    }
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