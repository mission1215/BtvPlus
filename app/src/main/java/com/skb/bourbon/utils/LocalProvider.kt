package com.skb.bourbon.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.skb.bourbon.main.SharedViewModel

val LocalSharedViewModel = compositionLocalOf<SharedViewModel> { error("No ViewModel provided") }
val LocalNaviHostController = compositionLocalOf<NavHostController> { error("No ViewModel provided") }