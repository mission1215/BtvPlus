package com.skb.btvplus.presenter.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.skb.btvplus.navigator.LandingItem
import com.skb.btvplus.navigator.SharedViewModel
import com.skb.mytvlibrary.navigator.Screens
import com.skb.mytvlibrary.navigator.navigationHostView
import timber.log.Timber

private const val TAG = "HomeScreen"

/**
 * Home screen
 *
 * @param sharedViewModel
 * @param navController
 */
@Composable
fun HomeScreen(sharedViewModel: SharedViewModel, navController: NavHostController) {
    Timber.d("$TAG:: init")
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Home Screen",
                fontSize = 45.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(contentColor = Color.Gray),
                onClick = {
                    navigationHostView(
                        navController,
                        sharedViewModel,
                        Screens.Detail.route,
                        LandingItem()
                    )
                },
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Detail 이동",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(hiltViewModel<SharedViewModel>(), NavHostController(LocalContext.current))
}