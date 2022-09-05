package com.tasneembohra.slackassignment.util.model

interface BaseItemUi {
    /**
     * Stable ID used as a unique identifier in lists.
     */
    val stableId: Long

    /**
     * Enforce equals function in items.
     */
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
}
