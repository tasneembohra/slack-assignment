package com.tasneembohra.slackassignment.util.base.adapters

import androidx.recyclerview.widget.DiffUtil
import com.tasneembohra.slackassignment.util.model.BaseItemUi

/**
 * Default [DiffUtil.ItemCallback] for [BaseItemUi].
 */
class BaseItemDiffUtilCallback<Item : BaseItemUi> : DiffUtil.ItemCallback<Item>() {
    /**
     * Function called to check whether two objects represent the same item.
     */
    override fun areItemsTheSame(old: Item, new: Item): Boolean {
        return old.stableId == new.stableId
    }

    /**
     * Function called to check whether two items have the same data.
     */
    override fun areContentsTheSame(old: Item, new: Item): Boolean {
        return old == new
    }
}
