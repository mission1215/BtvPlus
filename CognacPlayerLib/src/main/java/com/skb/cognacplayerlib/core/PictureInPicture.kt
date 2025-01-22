package com.skb.cognacplayerlib.core

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Rational
import androidx.annotation.RequiresApi
import com.skb.cognacplayerlib.util.findActivity
import timber.log.Timber

data class PipResource(
    var isPlaying: Boolean,
    val leftIcon: Int? = null,
    val centerIcon: List<Int>? = null,
    val rightIcon: Int? = null
)

private val TAG = "PictureInPicture"

@SuppressLint("ObsoleteSdkInt")
internal fun enterPictureInPictureMode(
    context: Context,
    resource: PipResource
) {
    try {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
            && canPipModeChange(context)
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val params = createPipParams(context, resource)
                context.findActivity().enterPictureInPictureMode(params)
            } else {
                context.findActivity().enterPictureInPictureMode()
            }
        }
    } catch (e: Exception) {
        Timber.d(TAG, "Error entering PIP mode : ${e.message}")
    }
}

@SuppressLint("ObsoleteSdkInt")
fun canPipModeChange(context: Context): Boolean {
    val appOps = context.findActivity().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager?
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        appOps?.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_PICTURE_IN_PICTURE,
            android.os.Process.myUid(),
            context.findActivity().packageName!!
        ) == AppOpsManager.MODE_ALLOWED
    } else {
        appOps?.checkOpNoThrow(
            AppOpsManager.OPSTR_PICTURE_IN_PICTURE,
            android.os.Process.myUid(),
            context.findActivity().packageName!!
        ) == AppOpsManager.MODE_ALLOWED
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun createPipParams(
    context: Context,
    resource: PipResource
): PictureInPictureParams {
    val params = PictureInPictureParams.Builder()
    val actions = ArrayList<RemoteAction>()

    resource.leftIcon?.let { icon ->
        val leftIcon = Icon.createWithResource(context, icon)
        val leftIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, PlaybackReceiver::class.java).apply { action = Action.Left.toString() },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        actions.add(RemoteAction(leftIcon, "left", "left", leftIntent))
    }

    resource.centerIcon?.let { icon ->
        val centerIcon = Icon.createWithResource(context, if (resource.isPlaying) icon[0] else icon[1])
        val centerIntent = PendingIntent.getBroadcast(
            context,
            if (resource.isPlaying) 1 else 2,
            Intent(context, PlaybackReceiver::class.java).apply { action = Action.Center.toString() },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        actions.add(RemoteAction(centerIcon, "center", "center", centerIntent))
    }

    resource.rightIcon?.let { icon ->
        val rightIcon = Icon.createWithResource(context, icon)
        val rightIntent = PendingIntent.getBroadcast(
            context,
            3,
            Intent(context, PlaybackReceiver::class.java).apply { action = Action.Right.toString() },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        actions.add(RemoteAction(rightIcon, "right", "right", rightIntent))
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        params.setTitle("Video Player").setAspectRatio(Rational(16, 9))
            .setSeamlessResizeEnabled(true)
    }
    params.setActions(actions)
    return params.build()
}

// isPlaying 값이 변경될 때 호출
@SuppressLint("ObsoleteSdkInt")
fun updatePipParams(context: Context, resource: PipResource) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        try {
            val params = createPipParams(context, resource)
            context.findActivity().setPictureInPictureParams(params)
        } catch (_: Exception) {
        }
    }
}

@SuppressLint("ObsoleteSdkInt")
fun Context.isActivityStatePipMode(): Boolean {
    val currentActivity = findActivity()

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        currentActivity.isInPictureInPictureMode
    } else {
        false
    }
}