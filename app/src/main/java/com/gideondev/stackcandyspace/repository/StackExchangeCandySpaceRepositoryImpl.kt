
package com.gideondev.stackcandyspace.repository

import androidx.annotation.WorkerThread
import com.gideondev.stackcandyspace.Utils.StringUtils
import com.gideondev.stackcandyspace.dataApiCall.ApiDataState
import com.gideondev.stackcandyspace.dataApiCall.remote.*
import com.gideondev.stackcandyspace.dataApiCall.remote.responses.SearchUserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * This is an implementation of [StackExchangeCandySpaceRepository] to handle communication with [StackExchangeCandySpaceApiService] server.
 * @author Gideon Oyediran
 */
class StackExchangeCandySpaceRepositoryImpl @Inject constructor(
    private val stringUtils: StringUtils,
    private val apiService: StackExchangeCandySpaceApiService
) : StackExchangeCandySpaceRepository {

    @WorkerThread
    override suspend fun searchUser(
        order: String,
        sort: String,
        inName: String,
        site: String
    ): Flow<ApiDataState<SearchUserResponse>> {

        return flow {
            apiService.searchForUser(site,inName,sort,order).apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(ApiDataState.success(it))
                    }
                }
                // handle the case when the API request gets an error response.
                // e.g. internal server error.
            }.onErrorSuspend {
                emit(ApiDataState.error<SearchUserResponse>(message()))

                // handle the case when the API request gets an exception response.
                // e.g. network connection error.
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(ApiDataState.error<SearchUserResponse>(stringUtils.noNetworkErrorMessage()))
                } else {
                    emit(ApiDataState.error<SearchUserResponse>(stringUtils.somethingWentWrong()))
                }
            }
        }
    }
}
