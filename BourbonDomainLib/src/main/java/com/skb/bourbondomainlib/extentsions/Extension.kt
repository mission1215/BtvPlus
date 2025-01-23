package com.skb.bourbondomainlib.extentsions

import android.net.Uri

fun String.getHost() = Uri.parse(this).host