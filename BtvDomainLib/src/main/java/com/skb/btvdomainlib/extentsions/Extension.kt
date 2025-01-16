package com.skb.btvdomainlib.extentsions

import android.net.Uri

fun String.getHost() = Uri.parse(this).host