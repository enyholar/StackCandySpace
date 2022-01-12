package com.gideondev.stackcandyspace.repository

import com.gideondev.stackcandyspace.dataApiCall.ApiDataState
import com.gideondev.stackcandyspace.dataApiCall.remote.responses.SearchUserResponse
import kotlinx.coroutines.flow.Flow

/**
 *  StackCandySpaceRepository is a data interface layer that allows you to communicate with any data source, either a server or a local database.
 * @see [StackExchangeCandySpaceRepositoryImpl] for implementation of this class to utilize Stack Exchange API.
 * @author Gideon Oyediran
 */
interface StackExchangeCandySpaceRepository {
    suspend fun searchUser(order: String, sort: String, inName: String, site: String): Flow<ApiDataState<SearchUserResponse>>
}
