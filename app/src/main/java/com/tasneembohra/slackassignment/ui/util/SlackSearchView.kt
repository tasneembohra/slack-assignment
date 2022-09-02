package com.tasneembohra.slackassignment.ui.util

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.MotionEvent
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import com.tasneembohra.slackassignment.R
import com.tasneembohra.slackassignment.ui.util.search.SearchBarConfig
import timber.log.Timber

class SlackSearchView(context: Context, attrs: AttributeSet) : SearchView(context, attrs) {

    var onInterceptTouchEvent: (() -> (Unit))? = null
    var searchBarConfig: SearchBarConfig? = null

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        onInterceptTouchEvent?.invoke()
        return onInterceptTouchEvent != null
    }

    fun bind(searchBarConfig: SearchBarConfig?, searchActiveListenerFromToolbar: ((searchActive: Boolean) -> Unit)?) {
        this.searchBarConfig = searchBarConfig
        if (searchBarConfig == null) {
            isVisible = false
            return
        }

        isVisible = true
        setIconifiedByDefault(false)
        // always reset the query without submitting query
        setQuery(searchBarConfig.initialQuery, false)
        clearFocus()
        // Set Search Query Listener
        setOnQueryTextListener(searchBarConfig.onSearchQueryListener, searchBarConfig.onSearchClearListener)
        // Set clear Listener
        setOnCloseListener(searchBarConfig.onSearchClearListener)

        // Set left icon resource and click listener.
        // setOnSearchClickListener only works in collapsed state.
        // setClickListener will work in expanded state but touch listener interferes with click listener
        // which is why we will set click listener on the icon directly.
        setupLeftIcon(searchBarConfig.leftDrawableRes, searchBarConfig.onSearchIconClickListener)

        setSearchViewTouchListener(searchBarConfig.onSearchViewClickListener, searchActiveListenerFromToolbar)
    }

    fun unbind() {
        searchBarConfig = null
        setOnQueryTextListener(null)
        setOnCloseListener(null)
        searchIcon?.setOnClickListener(null)
        setSearchViewTouchListener(null, null)
    }

    /*fun handleDeviceOnBackPress(callback: (() -> Unit)?): OnBackPressedCallback? {
        if (context is ContextThemeWrapper) {
            val ctx = (context as ContextThemeWrapper).baseContext
            if (ctx is BaseActivity) {
                return ctx.onBackPressedDispatcher.addCallback {
                    callback?.invoke()
                }
            }
        }
        Timber.d("Could not register OnBackPressedCallback due to non-activity context = %s", context)
        return null
    }*/
}

private fun SearchView.setOnQueryTextListener(
    onSearchQueryListener: (query: String) -> Unit,
    onSearchClearListener: () -> Unit
) {
    setOnQueryTextListener(
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onSearchQueryListener.invoke(query ?: "")
                clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    onSearchClearListener.invoke()
                }
                return false
            }
        }
    )
}

private fun SearchView.setOnCloseListener(onSearchClearListener: () -> Unit) {
    setOnCloseListener {
        onSearchClearListener.invoke()
        false
    }
}

private fun SearchView.setSearchViewTouchListener(
    onSearchViewClickListener: (() -> Unit)?,
    searchActiveListenerFromToolbar: ((searchActive: Boolean) -> Unit)?
) {
    // since search view is not iconified by default which means search view is in expanded state,
    // click listener alone is not enough.
    // We need to detect touch on the edit text to trigger click instead of opening keyboard
    if (this is SlackSearchView) {
        onInterceptTouchEvent = if (onSearchViewClickListener != null || searchActiveListenerFromToolbar != null) {
            {
                onSearchViewClickListener?.invoke()
                searchActiveListenerFromToolbar?.let { searchActiveListenerFromToolbar ->
                    var onBackPressedCallback: OnBackPressedCallback? = null

                    val onSearchBackIconPressed = {
                        // remove activity onBackPress event listener
                        onBackPressedCallback?.remove()

                        // undo the changes done with invoke earlier
                        searchActiveListenerFromToolbar.invoke(false)

                        // reset the touch listener so that additional touch listener
                        // could start to notify toolbar again.
                        setSearchViewTouchListener(
                            searchBarConfig?.onSearchViewClickListener,
                            searchActiveListenerFromToolbar
                        )
                    }

                    // set the left icon and callback
                    setupLeftIcon(R.drawable.ic_back_arrow, onSearchBackIconPressed)

                    // register same callback with activity onBackPressed event listener
                    //onBackPressedCallback = handleDeviceOnBackPress(onSearchBackIconPressed)

                    // reset the touch listener to nullify additional touch listener, since
                    // we want to let other icons (back arrow, close icon) of the search view react to click/touch
                    setSearchViewTouchListener(
                        searchBarConfig?.onSearchViewClickListener,
                        searchActiveListenerFromToolbar = null
                    )

                    // let toolbar handle the required changes on its children
                    searchActiveListenerFromToolbar.invoke(true)
                }
            }
        } else {
            null
        }
    }
}

fun SearchView.setupLeftIcon(
    @DrawableRes leftDrawableRes: Int,
    onSearchIconClickListener: (() -> Unit)?
) {
    searchIcon?.setImageResource(leftDrawableRes)
    if (onSearchIconClickListener != null) {
        searchIcon?.setOnClickListener {
            onSearchIconClickListener.invoke()
        }
    } else {
        searchIcon?.setOnClickListener(null)
    }
}

private val SearchView.searchIcon: ImageView?
    get() {
        return findViewById(androidx.appcompat.R.id.search_mag_icon)
    }
