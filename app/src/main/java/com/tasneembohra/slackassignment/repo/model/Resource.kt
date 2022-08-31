package com.tasneembohra.slackassignment.repo.model

import kotlinx.coroutines.CancellationException
import timber.log.Timber

sealed class Resource<out T : Any?>(
    open val data: T?,
) {
    data class Success<out T : Any?>(
        override val data: T,
    ) : Resource<T>(data)

    data class Error<out T : Any?>(
        val exception: Throwable,
        override val data: T? = null,
        val isCancelled: Boolean = false,
    ) : Resource<T>(data)

    data class Loading<out T : Any?>(
        override val data: T? = null,
    ) : Resource<T>(data)

    fun <R : Any> map(transform: (T) -> R): Resource<R> {
        return when (this) {
            is Success -> Success(data = transform(data))
            is Error -> Error(exception = exception, data = data?.let(transform))
            is Loading -> Loading(data = data?.let(transform))
        }
    }
}

@Suppress("TooGenericExceptionCaught")
inline fun <T : Any?> runResourceCatching(block: () -> T): Resource<T> {
    return try {
        Resource.Success(data = block())
    } catch (e: CancellationException) {
        Resource.Error(exception = e, isCancelled = true)
    } catch (e: Throwable) {
        Timber.e(e, "runResourceCatching error")
        Resource.Error(exception = e)
    }
}
