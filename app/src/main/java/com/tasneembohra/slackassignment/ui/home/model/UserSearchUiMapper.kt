package com.tasneembohra.slackassignment.ui.home.model

import com.tasneembohra.slackassignment.repo.model.User
import com.tasneembohra.slackassignment.util.base.model.Mapper
import com.tasneembohra.slackassignment.util.model.AvatarUi

object UserSearchUiMapper : Mapper<User, AvatarUi> {
    override fun map(from: User) = AvatarUi(
        id = from.uid.toString(),
        image = from.avatarUrl,
        title = from.username,
        subtitle = from.displayName,
    )
}