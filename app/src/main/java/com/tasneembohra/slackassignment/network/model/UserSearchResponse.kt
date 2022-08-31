package com.tasneembohra.slackassignment.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSearchResponse(val ok: Boolean, val users: List<User>)