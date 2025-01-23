package com.skb.bourbon.main


/**
 * Base nav items
 *
 * @constructor Create empty Base nav items
 */
sealed class BaseNavItems {
    data class HomeNavItem(val id: String? = null) : BaseNavItems()
    data class DetailNavItem(val id: String? = null) : BaseNavItems()
}