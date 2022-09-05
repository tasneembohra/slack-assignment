package com.tasneembohra.slackassignment.util.base.viewholders

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.core.view.updatePadding
import coil.load
import com.tasneembohra.slackassignment.R
import com.tasneembohra.slackassignment.databinding.ListItemAvatarBinding
import com.tasneembohra.slackassignment.util.extensions.orNoDataString
import com.tasneembohra.slackassignment.util.extensions.withShape
import com.tasneembohra.slackassignment.util.model.AvatarUi

class AvatarViewHolder(
    parent: ViewGroup,
    @DimenRes private val verticalPaddingResForRoot: Int = R.dimen.default_avatar_vertical_padding
) : BaseViewHolder<ListItemAvatarBinding, AvatarUi>(
    parent = parent,
    inflater = ListItemAvatarBinding::inflate,
) {
    override fun bindView(item: AvatarUi, savedState: Bundle?) {
        with(binding) {
            title.text = item.title.orNoDataString()
            subtitle.text = item.subtitle.orNoDataString()
            binding.avatar.withShape(R.style.ShapeAppearanceOverlay_Rounded)
            avatar.load(item.image)
            with(root) {
                val verticalPadding = resources.getDimensionPixelSize(verticalPaddingResForRoot)
                updatePadding(top = verticalPadding, bottom = verticalPadding)
            }
        }
    }
}
