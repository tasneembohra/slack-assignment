package com.tasneembohra.slackassignment.util.base.viewholders

import android.os.Bundle
import android.view.ViewGroup
import com.tasneembohra.slackassignment.databinding.ListItemErrorMessageBinding
import com.tasneembohra.slackassignment.util.model.ErrorUi

class ErrorViewHolder(
    parent: ViewGroup,
) : BaseViewHolder<ListItemErrorMessageBinding, ErrorUi>(
    parent = parent,
    inflater = ListItemErrorMessageBinding::inflate,
) {
    override fun bindView(item: ErrorUi, savedState: Bundle?) {
        with(binding) {
            errorMessage.text = errorMessage.context.getString(item.messageId)
        }
    }
}