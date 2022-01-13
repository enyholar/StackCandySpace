package com.gideondev.stackcandyspace.dataApiCall

/**
 * A sealed class for retrieving data from the server, which can be either data or an error.
 * @author Gideon Oyediran
 */
sealed class ApiDataState<T> {
    data class Success<T>(val data: T) : ApiDataState<T>()
    data class Error<T>(val message: String) : ApiDataState<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: String) = Error<T>(message)
    }
}
