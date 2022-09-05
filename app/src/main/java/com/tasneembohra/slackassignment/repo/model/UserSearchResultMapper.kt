package com.tasneembohra.slackassignment.repo.model

import com.tasneembohra.slackassignment.network.model.UserResponse
import com.tasneembohra.slackassignment.util.base.model.Mapper

object UserSearchResultMapper: Mapper<UserResponse, User> {
    override fun map(from: UserResponse) = User(
        uid = from.id,
        username = from.username,
        displayName = from.displayName,
        avatarUrl = from.avatarUrl,
    )
}