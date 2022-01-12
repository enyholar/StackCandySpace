package com.gideondev.stackcandyspace.dataApiCall.remote

import com.gideondev.stackcandyspace.dataApiCall.remote.responses.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StackExchangeCandySpaceApiService {

    @GET("users")
    suspend fun searchForUser(
        @Query("site") page: String = "stackoverflow",
        @Query("inname") query: String,
        @Query("sort") sort: String = "reputation",
        @Query("order") orderBy: String = "desc"
    ): ApiResponse<SearchUserResponse>
}
