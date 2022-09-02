package com.tasneembohra.slackassignment.ui.util.search

import androidx.annotation.DrawableRes
import com.tasneembohra.slackassignment.R

data class SearchBarConfig(
    /** Drawable Resource for left icon. Defaults to search icon */
    @DrawableRes val leftDrawableRes: Int = R.drawable.ic_search_grey,
    /** pre-fill search view with query without submitting */
    val initialQuery: String = "",
    val onSearchQueryListener: (query: String) -> Unit,
    val onSearchClearListener: () -> Unit,
    /** listener for full search view click event*/
    val onSearchViewClickListener: (() -> Unit)? = null,
    /** listener for left icon click event */
    val onSearchIconClickListener: (() -> Unit)? = null,
    /** If search bar itself wants to show the back button */
    val takeOverBackNavigationButton: Boolean = false,
    /**
     * Non-null value will hide primary content of the toolbar and notify when search bar is touched.
     * Tapping back arrow within search bar will un-hide toolbar primary content and notify.
     * @param searchActive true if search bar is active, false otherwise
     * */
    val onSearchActiveListener: ((searchActive: Boolean) -> Unit)? = null,
)
