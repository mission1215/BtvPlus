package com.skb.btvplus.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.skb.btvplus.main.SharedViewModel

val LocalSharedViewModel = compositionLocalOf<SharedViewModel> { error("No ViewModel provided") }
val LocalNaviHostController = compositionLocalOf<NavHostController> { error("No ViewModel provided") }