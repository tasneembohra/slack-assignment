package com.tasneembohra.slackassignment.util.base.adapters

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tasneembohra.slackassignment.repo.model.Resource
import com.tasneembohra.slackassignment.util.model.BaseItemUi
import com.tasneembohra.slackassignment.util.base.viewholders.BaseViewHolder
import com.tasneembohra.slackassignment.util.base.viewholderfactory.BaseViewHolderFactory
import kotlin.properties.Delegates

open class BaseListAdapter(
    private val viewHolderFactory: BaseViewHolderFactory,
    diffUtilCallback: BaseItemDiffUtilCallback<BaseItemUi> = BaseItemDiffUtilCallback()
) : RecyclerView.Adapter<BaseViewHolder<*, BaseItemUi>>(), ListAdapter {

    private val instanceStates: MutableMap<Long, Bundle> = hashMapOf()

    // TODO try to use RecyclerView.ListAdapter<BaseItem, VH>(diffUtilCallback) instead.
    private var _data: List<BaseItemUi> by Delegates.observable(emptyList()) { _, old, new ->
        val diff = DiffUtil.calculateDiff(
            object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return old[oldItemPosition].stableId == new[newItemPosition].stableId
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return old[oldItemPosition] == new[newItemPosition]
                }

                override fun getOldListSize() = old.size

                override fun getNewListSize() = new.size

                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                    // see [diffUtilCallback.getChangePayload] docs
                    return diffUtilCallback.getChangePayload(old[oldItemPosition], new[newItemPosition])
                }
            }
        )

        diff.dispatchUpdatesTo(this)
    }

    init {
        // Avoid That RecyclerView’s Views are Blinking when notifyDataSetChanged.
        super.setHasStableIds(true)
    }

    fun setData(resource: Resource<List<BaseItemUi>>) {
        _data = resource.data.orEmpty()
    }

    @Suppress("UNCHECKED_CAST")
    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<*, BaseItemUi> {
        return viewHolderFactory.createViewHolder(parent, viewType) as BaseViewHolder<*, BaseItemUi>
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.stableId ?: super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return _data.size
    }

    final override fun getItemViewType(position: Int): Int {
        return viewHolderFactory.getItemLayoutId(getItem(position))
    }

    final override fun onBindViewHolder(holder: BaseViewHolder<*, BaseItemUi>, position: Int) {
        val item = getItem(position)
        if (item == null) {
            holder.recycleView()
        } else {
            holder.bindView(item, instanceStates[holder.itemId])
        }
    }

    final override fun onViewRecycled(holder: BaseViewHolder<*, BaseItemUi>) {
        val instanceState = holder.recycleView()
        if (instanceState == null) {
            instanceStates.remove(holder.itemId)
        } else {
            instanceStates[holder.itemId] = instanceState
        }
        super.onViewRecycled(holder)
    }

    final override fun getItem(position: Int): BaseItemUi? {
        return _data.getOrNull(position)
    }
}
