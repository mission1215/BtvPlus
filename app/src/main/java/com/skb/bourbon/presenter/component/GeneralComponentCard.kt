package com.skb.bourbon.presenter.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class GeneralComponentCardItem(
    var imgUrl: String? = null,
    var title: String? = null,
)

data class CallbackItem(var id: String? = null)
/**
 * General component card
 *
 * @param modifier
 * @param item
 * @param onClick
 * @receiver
 */
@Composable
fun GeneralComponentCard(
    modifier: Modifier,
    item: GeneralComponentCardItem,
    onClick: (id: CallbackItem) -> Unit,
) {
    Card(modifier = modifier) {
        Box(
            modifier = Modifier.clickable {
                onClick(CallbackItem(""))
            },
        ) {
            GeneralImageView(
                modifier = Modifier.fillMaxWidth(), imagePath = item.imgUrl
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = item.title ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 16.dp),
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary // 텍스트 색상
            )
        }
    }
}