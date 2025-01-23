package com.skb.bourbon_network.extentsions

import android.net.Uri

fun String.getHost() = Uri.parse(this).host