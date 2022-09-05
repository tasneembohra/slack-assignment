package com.tasneembohra.slackassignment.util.base.viewholders

import android.os.Bundle
import android.view.ViewGroup
import coil.load
import com.tasneembohra.slackassignment.databinding.ListItemAvatarBinding
import com.tasneembohra.slackassignment.util.extensions.orNoDataString
import com.tasneembohra.slackassignment.util.model.AvatarUi

class AvatarViewHolder(
    parent: ViewGroup,
) : BaseViewHolder<ListItemAvatarBinding, AvatarUi>(
    parent = parent,
    inflater = ListItemAvatarBinding::inflate,
) {
    override fun bindView(item: AvatarUi, savedState: Bundle?) {
        with(binding) {
            title.text = item.title.orNoDataString()
            subtitle.text = item.subtitle.orNoDataString()
            avatar.load(item.image)
        }
    }
}
