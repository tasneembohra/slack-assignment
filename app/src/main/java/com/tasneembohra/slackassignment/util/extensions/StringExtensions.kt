package com.tasneembohra.slackassignment.util.extensions

const val NO_DATA_STRING = "—"

/**
 * Return `—` if [this] is null or blank.
 */
fun String?.orNoDataString(placeholder: String = NO_DATA_STRING): String {
    return if (this.isNullOrBlank()) {
        placeholder
    } else {
        this
    }
}
