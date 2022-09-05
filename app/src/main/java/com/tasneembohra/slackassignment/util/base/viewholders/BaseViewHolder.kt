package com.tasneembohra.slackassignment.util.base.viewholders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tasneembohra.slackassignment.util.extensions.ViewBindingInflater
import com.tasneembohra.slackassignment.util.model.BaseItemUi

/**
 * Base view holder to standardize and simplify initialization for this component.
 *
 * @param binding View binding generated class instance.
 *
 * @see RecyclerView.ViewHolder
 */
abstract class BaseViewHolder<VB : ViewBinding, Item : BaseItemUi> private constructor(
    protected val binding: VB,
) : RecyclerView.ViewHolder(binding.root) {

    constructor(
        parent: ViewGroup,
        inflater: ViewBindingInflater<VB>,
    ) : this(inflater(LayoutInflater.from(parent.context), parent, false))

    /**
     * Bind an item to its view.
     *
     * @param item Item to bind.
     * @param savedState item's saved state.
     */
    abstract fun bindView(item: Item, savedState: Bundle?)

    /**
     * Function called when a view is being recycled.
     *
     * @return Item's state to be saved.
     */
    open fun recycleView(): Bundle? {
        return null
    }
}
