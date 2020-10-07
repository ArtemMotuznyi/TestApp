package com.developerartemmotuznyi.pikabufeed.core

sealed class ActionResult<out R> {

    class Success<out T>(val data: T) : ActionResult<T>()
    class Error(val exception: Exception) : ActionResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }


    inline fun handle(onSuccess: (R) -> Unit, onError: (Exception) -> Unit) {
        when (this) {
            is Success -> onSuccess(data)
            is Error -> onError(this.exception)
        }
    }

    inline fun <T> transform(transform: (R) -> T): ActionResult<T> =
        try {
            when (this) {
                is Error -> this
                is Success -> Success(transform(this.data))
            }
        } catch (e: Exception) {
            Error(e)
        }

    suspend fun <T, Result> join(
        joinWith: ActionResult<T>,
        join: suspend (R, T) -> Result
    ): ActionResult<Result> {
        val mainData = when (this) {
            is Success -> data
            is Error -> return this
        }

        val joinWithData = when (joinWith) {
            is Success -> joinWith.data
            is Error -> return joinWith
        }

        return Success(join(mainData, joinWithData))
    }
}
