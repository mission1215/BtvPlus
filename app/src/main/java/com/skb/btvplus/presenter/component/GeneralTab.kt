package com.skb.btvplus.presenter.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class TabItem(
    val id: String? = null, // default: SHLF_101
    val title: String? = null, // default: 전체 선반이름 (크림버전 상단 탭)
    val imgRes: Int? = null,
)

/**
 * General tab
 *
 * @param tabs
 * @param selectedTabIndex
 * @param modifier
 * @param onTabSelected
 * @receiver
 */
@Composable
fun GeneralTab(
    tabs: List<TabItem>,
    selectedTabIndex: Int,
    modifier: Modifier,
    onTabSelected: (Int) -> Unit,
) {
    // 현재 선택된 탭 상태 관리
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = modifier) {
        // TabRow: 상단 탭 UI
        TabRow(selectedTabIndex = selectedTabIndex,
            containerColor = Color.White, // 배경색
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer, // 텍스트 및 아이콘 색상
            indicator = { tabPositions ->
                tabPositions.forEachIndexed { index, tabPosition ->
                    val color = if (index == selectedTabIndex) Color.DarkGray else Color.Transparent
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPosition),
                        height = 2.dp,
                        color = color
                    )
                }
            }) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    modifier = Modifier,
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        onTabSelected(index)
                    },
                    text = { Text(text = tab.title ?: "") }, // 탭 제목
                    icon = {},
                )
            }
        }
    }
}