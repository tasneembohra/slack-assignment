package com.tasneembohra.slackassignment.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * User model returned by the API.
 */
@Serializable
data class UserResponse(
    val username: String,
    @SerialName("id")
    val id: Long,
    @SerialName("display_name")
    val displayName: String,
    @SerialName("avatar_url")
    val avatarUrl: String
) : BaseResponse()