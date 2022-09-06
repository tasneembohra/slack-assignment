package com.tasneembohra.slackassignment.repo.model

import com.tasneembohra.slackassignment.repo.model.ErrorCode.NO_INTERNET
import com.tasneembohra.slackassignment.repo.model.ErrorCode.SOMETHING_ELSE
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

enum class ErrorCode { NOT_FOUND, NO_INTERNET, SOMETHING_ELSE }

sealed class Resource<out T : Any?>(
    open val data: T?,
    open val errorCode: ErrorCode? = null,
) {
    data class Success<out T : Any?>(
        override val data: T,
    ) : Resource<T>(data)

    data class Error<out T : Any?>(
        val exception: Throwable? = null,
        override val errorCode: ErrorCode? = null,
        override val data: T? = null,
    ) : Resource<T>(data, errorCode)

    data class Loading<out T : Any?>(
        override val data: T? = null,
    ) : Resource<T>(data)

    fun <R : Any> map(transform: (T?) -> R): Resource<R> {
        return when (this) {
            is Success -> Success(data = transform(data))
            is Error -> Error(
                exception = exception,
                errorCode = errorCode,
                data = transform(data)
            )
            is Loading -> Loading(data = data?.let(transform))
        }
    }
}


inline fun <T : Any?> runResourceCatching(block: () -> T): Resource<T> {
    return try {
        Resource.Success(data = block())
    } catch (e: UnknownHostException) {
        Resource.Error(exception = e, errorCode = NO_INTERNET)
    } catch (e: SocketTimeoutException) {
        Resource.Error(exception = e, errorCode = NO_INTERNET)
    } catch (e: Throwable) {
        Timber.e(e, "runResourceCatching error")
        Resource.Error(exception = e, errorCode = SOMETHING_ELSE)
    }
}
