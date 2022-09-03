package com.tasneembohra.slackassignment.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse {
    @SerialName("error")
    open val error: String? = null
    @SerialName("ok")
    open val ok: Boolean = false
}