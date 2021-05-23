package fi.monad.asteroidradar.api

import retrofit2.Response
import java.lang.Exception

data class Result<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?
) {

    companion object {
        fun <T> ok(data: Response<T>): Result<T> {
            return Result(
                status = Status.Ok,
                data = data,
                exception = null
            )
        }

        fun <T> failure(exception: Exception): Result<T> {
            return Result(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }
    }

    sealed class Status {
        object Ok : Status()
        object Failure : Status()
    }

    val failed: Boolean
        get() = this.status == Status.Failure

    val ok: Boolean
        get() = !this.failed && this.data?.isSuccessful == true

    val body: T
        get() = this.data!!.body()!!
}

inline fun <T, V> Result<T>.map(transform: (Response<T>) -> V): Result<V> {
    return when (this.status) {
        is Result.Status.Ok -> try {
            Result.ok(Response.success(transform.invoke(this.data!!)))
        } catch (e: Exception) {
            Result.failure(e)
        }
        is Result.Status.Failure -> Result.failure<V>(this.exception!!)
    }
}