package com.skb.bourbon.presenter.component

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlin.let
import kotlin.text.isNullOrEmpty

sealed class ImageState {
    data object NONE : ImageState()
    data object Success : ImageState()
    data object Error : ImageState()
    data object Loading : ImageState()
}

/**
 * General image view
 *
 * @param modifier
 * @param imagePath
 * @param defaultImage
 * @param isCache
 * @param isDim
 * @param contentScale
 * @param onBitmapLoaded
 * @param onError
 * @receiver
 * @receiver
 */
@Composable
fun GeneralImageView(
    modifier: Modifier = Modifier,
    imagePath: String?,
//    defaultImage: Painter? = painterResource(R.drawable.ic_media),
    isCache: Boolean = true,
    isDim: Boolean = false,
    contentScale: ContentScale = ContentScale.FillWidth,
    onBitmapLoaded: (Bitmap) -> Unit = {},
    onError: () -> Unit = {},
) {
    val context = LocalContext.current

    Box(modifier = modifier) {
        if (imagePath.isNullOrEmpty()) {
            // 기본 이미지 표시
//            Image(
//                modifier = modifier,
//                painter = defaultImage,
//                contentDescription = "Default Icon",
//            )
        } else {
            // Coil의 ImageRequest 생성
            val imageRequest = ImageRequest.Builder(context).data(imagePath).crossfade(true).build()

            var bitmap by remember { mutableStateOf<Bitmap?>(null) }
            val painter = rememberAsyncImagePainter(model = imageRequest)

            SubcomposeAsyncImage(model = imageRequest,
                contentDescription = null,
                modifier = modifier,
                contentScale = contentScale,
                loading = {
//                    DefaultMediaImage(Modifier, defaultImage)
                },
                error = {
//                    DefaultMediaImage(Modifier, defaultImage)
                    onError()
                })

            // Flow를 사용하여 painter 상태 관찰 및 처리
            LaunchedEffect(painter) {
                snapshotFlow { painter.state }.collect { state ->
                        if (state is AsyncImagePainter.State.Success) {
                            val bitmapDrawable = state.result.drawable as? BitmapDrawable
                            bitmap = bitmapDrawable?.bitmap
                            bitmap?.let(onBitmapLoaded)
                        }
                    }
            }
        }

        // Dim 효과 추가
        if (isDim) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.5f)) // 반투명 검정색 Dim 효과
            )
        }
    }
}

@Composable
fun DefaultMediaImage(modifier: Modifier, defaultImage: Painter) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = defaultImage, contentDescription = "Default Icon", tint = Color.Gray
        )
    }
}


@Preview(name = "PreviewGeneralImageView", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "PreviewGeneralImageView", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewGeneralImageView() {
    GeneralImageView(Modifier, "") {}
}