package com.skb.btvplus.presenter.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skb.btvplus.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GeneralAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: (() -> Unit)? = null, // 뒤로가기 버튼 클릭 핸들러 (null이면 버튼 숨김)
    actions: @Composable RowScope.() -> Unit = {}, // 추가 액션 버튼들
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    TopAppBar(modifier = modifier
        .fillMaxWidth()
        .heightIn(min = 56.dp, max = 90.dp), title = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally)
        )

    }, navigationIcon = {
        if (onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painterResource(R.drawable.ic_backward),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }
    }, actions = actions, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.White, // 배경색
        titleContentColor = Color.Black // 제목 색상
    ), scrollBehavior = scrollBehavior
    )
}