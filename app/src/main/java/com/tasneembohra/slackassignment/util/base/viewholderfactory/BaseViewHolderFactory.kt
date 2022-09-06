package com.tasneembohra.slackassignment.util.base.viewholderfactory

import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.tasneembohra.slackassignment.R
import com.tasneembohra.slackassignment.util.base.viewholders.AvatarViewHolder
import com.tasneembohra.slackassignment.util.base.viewholders.BaseViewHolder
import com.tasneembohra.slackassignment.util.base.viewholders.ErrorViewHolder
import com.tasneembohra.slackassignment.util.model.AvatarUi
import com.tasneembohra.slackassignment.util.model.BaseItemUi
import com.tasneembohra.slackassignment.util.model.ErrorUi

/**
 * This factory handle the creation of [BaseViewHolder].
 */
open class BaseViewHolderFactory {

    /**
     * Return the associated layout ID for this [item].
     * It will be used by [createViewHolder] to create the correct [BaseViewHolder] for this [item].
     *
     * @throws IllegalArgumentException When an unknown [item] is passed to this function.
     */
    @CallSuper
    @Throws(IllegalArgumentException::class)
    @LayoutRes
    open fun getItemLayoutId(item: BaseItemUi?): Int {
        return when (item) {
            is AvatarUi -> R.layout.list_item_avatar
            is ErrorUi -> R.layout.list_item_error_message
            else -> throw IllegalArgumentException("getItemLayoutId: Unknown item $item")
        }
    }

    /**
     * Create a [BaseViewHolder] associated with this [layoutId].
     * [layoutId] is returned by [getItemLayoutId].
     *
     * @throws IllegalArgumentException When an unknown [layoutId] is passed to this function.
     */
    @CallSuper
    @Throws(IllegalArgumentException::class)
    open fun createViewHolder(parent: ViewGroup, @LayoutRes layoutId: Int): BaseViewHolder<*, *> {
        return when (layoutId) {
            R.layout.list_item_avatar -> createAvatarViewHolder(parent)
            R.layout.list_item_error_message -> createErrorViewHolder(parent)
            else -> throw IllegalArgumentException("createViewHolder: Unknown layoutId $layoutId")
        }
    }

    private fun createAvatarViewHolder(parent: ViewGroup): AvatarViewHolder {
        return AvatarViewHolder(parent)
    }

    private fun createErrorViewHolder(parent: ViewGroup): ErrorViewHolder {
        return ErrorViewHolder(parent)
    }
}
