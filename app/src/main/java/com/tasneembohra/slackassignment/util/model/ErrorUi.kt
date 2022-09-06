package com.tasneembohra.slackassignment.util.model

import androidx.annotation.StringRes
import com.tasneembohra.slackassignment.util.extensions.hashcode

data class ErrorUi(
    @StringRes val messageId: Int,
    override val stableId: Long = "ErrorUi - $messageId".hashcode()
): BaseItemUi
