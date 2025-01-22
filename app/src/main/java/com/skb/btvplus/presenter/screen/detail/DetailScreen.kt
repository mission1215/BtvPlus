package com.skb.btvplus.presenter.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.skb.btvplus.navigator.LandingItem
import com.skb.btvplus.presenter.component.GeneralComponentCard
import com.skb.btvplus.presenter.component.GeneralComponentCardItem
import com.skb.btvplus.presenter.screen.home.HandleNavigationEvents
import com.skb.btvplus.utils.LocalSharedViewModel
import timber.log.Timber

/**
 * Detail screen
 *
 * @param detailViewModel
 */
@Composable
fun DetailScreen(
    landingItem: LandingItem?,
    detailViewModel: DetailViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    Timber.d("DetailScreen: ${(landingItem as LandingItem)} ")

    // FIXME: shared Data 사용법
    val sharedData = LocalSharedViewModel.current.sharedData.collectAsStateWithLifecycle()
    Timber.d("sharedData: ${sharedData.value?.title}")

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {},
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Detail Screen",
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                GeneralComponentCard(modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                    item = GeneralComponentCardItem(
                        imgUrl = "https://www.esquirekorea.co.kr/resources_old/online/org_online_image/eq/3576376c-ed00-4025-b103-ff6fe5ff00a9.jpg",
                        title = "Player"
                    ),
                    onClick = {
                        Timber.d("GeneralComponentCard onClick :: $it")
                    })

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(contentColor = Color.Gray),
                    onClick = {
                        navController.popBackStack()
                    },
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Home으로 이동",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}