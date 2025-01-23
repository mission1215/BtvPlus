package com.skb.bourbon.main

import kotlinx.serialization.Serializable


/**
 * Base nav items
 *
 * @constructor Create empty Base nav items
 */
@Serializable
sealed class BaseNavItems {
    @Serializable
    data class HomeNavItem(val id: String? = null) : BaseNavItems()

    @Serializable
    data class DetailNavItem(val id: String? = null) : BaseNavItems()
}