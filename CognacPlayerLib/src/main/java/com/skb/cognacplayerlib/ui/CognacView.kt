package com.skb.medialibrary.ui

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.skb.cognacplayerlib.core.Action
import com.skb.cognacplayerlib.core.PipResource
import com.skb.cognacplayerlib.core.PlaybackActionBus
import com.skb.cognacplayerlib.core.enterPictureInPictureMode
import com.skb.cognacplayerlib.core.updatePipParams
import com.skb.cognacplayerlib.ui.CognacViewModel
import com.skb.cognacplayerlib.ui.CognacViewModel.ViewEffect.PictureInPitureMode

@OptIn(UnstableApi::class)
@Composable
fun CognacView(
    modifier: Modifier = Modifier,
    cognacViewModel: CognacViewModel
) {
    val context = LocalContext.current
    val player by rememberUpdatedState(cognacViewModel.getPlayer())
    val view = remember {
        PlayerView(context).apply {
            useController = false
            this.player = player
        }
    }
    var pipResource by remember { mutableStateOf<PipResource?>(null) }

    DisposableEffect(cognacViewModel) {
        cognacViewModel.setPlayerView(view)
        onDispose {
            cognacViewModel.release()
        }
    }

    AndroidView(
        factory = { view },
        modifier = modifier
    )

    LaunchedEffect(Unit) {
        PlaybackActionBus.actions.collect { action ->
            when (action) {
                Action.Left -> {
                    cognacViewModel.seekTo(player.currentPosition - 10000)
                }
                Action.Center -> {
                    cognacViewModel.togglePlayState()
                    pipResource?.let { resource ->
                        resource.isPlaying = player.isPlaying
                        updatePipParams(context, resource)
                    }
                }
                Action.Right -> {
                    cognacViewModel.seekTo(player.currentPosition + 10000)
                }
            }
        }
    }

    LaunchedEffect(cognacViewModel) {
        cognacViewModel.viewEffect.collect { flow ->
            when (flow) {
                is PictureInPitureMode -> {
                    view.useController = false
                    enterPictureInPictureMode(context, flow.resource)
                    pipResource = flow.resource
                }
            }
        }
    }
}