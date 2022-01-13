package com.gideondev.stackcandyspace.dataApiCall.remote.api
import com.gideondev.stackcandyspace.dataApiCall.remote.ApiResponse
import retrofit2.Response

object ApiUtil {

    fun <T : Any> successCall(data: T) = createCall(Response.success(data))

    fun <T : Any> createCall(response: Response<T>): ApiResponse<T> =
        ApiResponse.create(200..229, response)
}
