package com.gideondev.stackcandyspace.dataApiCall

/**
 * A generic class that keeps track of the loading state of a value.
 * @param <T>
</T> */
data class ApiDataResource<out T>(
    val status: DataStatus,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T?): ApiDataResource<T> {
            return ApiDataResource(DataStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): ApiDataResource<T> {
            return ApiDataResource(DataStatus.ERROR, data, msg)
        }
    }
}

/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class DataStatus {
    SUCCESS,
    ERROR
}
