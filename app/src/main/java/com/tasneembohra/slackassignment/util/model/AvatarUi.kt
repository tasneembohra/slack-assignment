package com.tasneembohra.slackassignment.util.model

import com.tasneembohra.slackassignment.util.extensions.hashcode

data class AvatarUi(
    val id: String,
    val image: String?,
    val title: String?,
    val subtitle: String?
) : BaseItemUi {
    override val stableId: Long = "Avatar_${id.hashCode()}".hashcode()
}
