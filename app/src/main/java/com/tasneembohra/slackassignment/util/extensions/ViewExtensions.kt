package com.tasneembohra.slackassignment.util.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * Type alias representing a [ViewBinding]'s inflater function.
 */
typealias ViewBindingInflater<ViewBinding> = (
    inflater: LayoutInflater,
    container: ViewGroup?,
    attachToRoot: Boolean
) -> ViewBinding