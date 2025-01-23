package com.skb.bourbon_utils

import com.google.gson.Gson

/**
 * To json
 *
 * @param T
 * @return
 */
fun <T> T.toJson(): String {
    return Gson().toJson(this)
}

/**
 * From json
 *
 * @param T
 * @return
 */
inline fun <reified T> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}
