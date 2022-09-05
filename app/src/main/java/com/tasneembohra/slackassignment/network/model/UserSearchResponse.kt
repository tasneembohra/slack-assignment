package com.tasneembohra.slackassignment.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSearchResponse(
    @SerialName("users")
    val users: List<UserResponse> = emptyList(),
): BaseResponse()