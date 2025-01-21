package com.skb.btvplus.presenter.screen.home

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.skb.btvdomainlib.network.UiState
import com.skb.btvplus.navigator.LandingViewModel
import com.skb.btvplus.presenter.component.GeneralAppBar
import com.skb.btvplus.presenter.component.GeneralComponentCard
import com.skb.btvplus.presenter.component.GeneralComponentCardItem
import com.skb.btvplus.presenter.component.GeneralTab
import com.skb.btvplus.presenter.component.ObserveLifeCycleEvents
import com.skb.btvplus.presenter.component.TabItem
import com.skb.btvplus.presenter.screen.detail.DetailLandingItem
import com.skb.mytvlibrary.navigator.Screens
import com.skb.mytvlibrary.navigator.navigationHostView
import timber.log.Timber

private const val TAG = "HomeScreen"

/**
 * Home screen
 *
 * @param landingViewModel
 * @param navController
 */

@Composable
fun HomeScreen(
    landingViewModel: LandingViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    Timber.d("$TAG:: init")
    val landingItem = landingViewModel.landingItemState.collectAsState(null)
    Timber.d("landingItem: $landingItem")
    LaunchedEffect(key1 = landingItem) {

    }

    HandleNavigationEvents(homeViewModel, navController)
    ObserveLifeCycleEvents(
        callbackEvent = {
            Timber.d("ObserveLifeCycleEvents $it")
            when (it) {
                Lifecycle.Event.ON_CREATE -> {}
                Lifecycle.Event.ON_START -> {}
                Lifecycle.Event.ON_RESUME -> {}
                Lifecycle.Event.ON_PAUSE -> {}
                Lifecycle.Event.ON_STOP -> {}
                Lifecycle.Event.ON_DESTROY -> {}
                Lifecycle.Event.ON_ANY -> {}
            }
        },
        onDisPoseCallback = {

        },
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopBar(homeViewModel, navController)
        },
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    PaddingValues(
                        start = padding.calculateLeftPadding(LayoutDirection.Ltr),
                        end = padding.calculateLeftPadding(LayoutDirection.Rtl),
                        top = 60.dp,
                        bottom = padding.calculateBottomPadding()
                    )
                ),
            color = Color.Transparent,
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // LayoutTab 추가
                LayoutTab(
                    modifier = Modifier.fillMaxWidth(),// 적절한 높이 설정
                    homeViewModel = homeViewModel
                )
                Spacer(modifier = Modifier.height(10.dp))
                // LayoutContainer 추가
                LayoutContainer(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .weight(1f), // Column 내에서 균형 있게 공간 배분
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}

@Composable
fun TopBar(homeVewModel: HomeViewModel, navController: NavHostController) {
    val context = navController.context
    GeneralAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = "Btv",
        onBackClick = {
            Timber.d("$TAG:: onBackClick")
            (context as? Activity)?.finish()
        },
    )
}

@Composable
fun LayoutTab(modifier: Modifier, homeViewModel: HomeViewModel) {
    val tabs = homeViewModel.tabState.collectAsStateWithLifecycle().value
    val rememberCoroutineScope = rememberCoroutineScope()
    LaunchedEffect(tabs) {
        Timber.d("LaunchedEffect tabs :: $tabs")
    }
    when (tabs) {
        is UiState.Error -> {}
        UiState.Loading -> {}
        is UiState.Success -> {
            tabs.data.data?.map {
                TabItem(id = it.shelfId, title = it.shelfName)
            }?.let {
                val tabs = listOf(TabItem(it.get(0).id, "홈"), TabItem(it.get(0).id, "서브"))
                GeneralTab(tabs = tabs, selectedTabIndex = 0, modifier) {
                    Timber.d("GeneralTab selectedTabIndex :: $it")
                    homeViewModel.updateSelectedTabIndex(it)
                }
                Timber.d("rememberCoroutineScope launch : tab 0 position set")
            }
        }
    }
}

@Composable
fun LayoutContainer(modifier: Modifier, homeViewModel: HomeViewModel) {
    val selectedTabIndex = homeViewModel.selectedTabIndexState.collectAsStateWithLifecycle().value
    val response = homeViewModel.contentsState.collectAsStateWithLifecycle().value
    val scrollState = rememberLazyListState()

    LaunchedEffect(selectedTabIndex) {
        Timber.tag(TAG).d("LaunchedEffect selectedTabIndex :: $selectedTabIndex")
        updateContent(selectedTabIndex, homeViewModel)
    }

    when (response) {
        is UiState.Error -> {}
        is UiState.Loading -> {}
        is UiState.Success -> {
            val list = response.data.data?.items
            LazyColumn(
                modifier = modifier,
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(list?.size ?: 0) { index ->
                    Timber.tag(TAG).d("LazyColumn items :: $index")
                    val item = list?.get(index)
                    val imgUrl = item?.item?.items?.get(0)?.content?._imagePath
                    val title = item?.item?.cardName

                    GeneralComponentCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp),
                        item = GeneralComponentCardItem(
                            imgUrl = "https://www.esquirekorea.co.kr/resources_old/online/org_online_image/eq/3576376c-ed00-4025-b103-ff6fe5ff00a9.jpg",
                            title = title
                        ),
                        onClick = {
                            Timber.tag(TAG).d("GeneralComponentCard onClick :: $it")
                            homeViewModel.sendEvent(
                                NavigationEvent.NavigateToDetail(
                                    DetailLandingItem(it.id)
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HandleNavigationEvents(homeViewModel: HomeViewModel, navController: NavHostController) {
    val navigationEvent = homeViewModel.navigationEventState.collectAsStateWithLifecycle(null).value
    Timber.d("EventListener $navigationEvent")
    when (navigationEvent) {
        is NavigationEvent.NavigateToDetail -> {
            navigationHostView(
                navController,
                landingViewModel = hiltViewModel(),
                Screens.Detail.route,
                DetailLandingItem(id = "detail test"),
            )
        }

        null -> {
            Timber.d("EventListener null")
        }
    }
}

fun updateContent(selectedTabIndex: Int, homeViewModel: HomeViewModel) {
    homeViewModel.requestGetExternalShelf(selectedTabIndex)
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(
        landingViewModel = hiltViewModel<LandingViewModel>(),
        navController = NavHostController(LocalContext.current)
    )
}