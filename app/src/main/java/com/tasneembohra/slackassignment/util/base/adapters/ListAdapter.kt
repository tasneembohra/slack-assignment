package com.tasneembohra.slackassignment.util.base.adapters

import com.tasneembohra.slackassignment.util.model.BaseItemUi


interface ListAdapter {
    fun getItem(position: Int): BaseItemUi?
    fun getItemCount(): Int
}
