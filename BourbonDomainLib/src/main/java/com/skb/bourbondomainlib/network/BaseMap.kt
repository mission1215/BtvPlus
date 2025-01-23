package com.skb.bourbondomainlib.network

import android.text.TextUtils
import kotlin.collections.set

/**
 * Class: baseMap
 * Created by jungsickkim on 2022/04/18.
 * Description:
 */
open class BaseMap {
    var mBaseMap: HashMap<String?, String?> = HashMap()

    fun add(key: String?, value: String?) {
        if (!TextUtils.isEmpty(value)) {
            mBaseMap[key] = value
        }
    }

    fun remove(tag: String, value: String) {
        mBaseMap.remove(tag)
        add(tag, value)
    }

    fun getHeader(): HashMap<String?, String?> {
        return mBaseMap
    }
}