package com.gideondev.stackcandyspace.dataApiCall.remote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gideondev.stackcandyspace.MainCoroutinesRule
import com.gideondev.stackcandyspace.Utils.StringUtils
import com.gideondev.stackcandyspace.dataApiCall.ApiDataState
import com.gideondev.stackcandyspace.dataApiCall.remote.ApiResponse
import com.gideondev.stackcandyspace.dataApiCall.remote.StackExchangeCandySpaceApiService
import com.gideondev.stackcandyspace.dataApiCall.remote.api.ApiUtil.successCall
import com.gideondev.stackcandyspace.dataApiCall.remote.message
import com.gideondev.stackcandyspace.dataApiCall.remote.responses.SearchUserResponse
import com.gideondev.stackcandyspace.repository.StackExchangeCandySpaceRepositoryImpl
import com.gideondev.stackcandyspace.test_common.MockTestUtil
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class StackExchangeCandySpaceRepositoryImplTest {

    // Subject under test
    private lateinit var repository: StackExchangeCandySpaceRepositoryImpl

    @MockK
    private lateinit var apiService: StackExchangeCandySpaceApiService

    @MockK
    private lateinit var stringUtils: StringUtils

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test searchForUsers() gives list of users`() = runBlocking {
        // Given
        repository = StackExchangeCandySpaceRepositoryImpl(stringUtils, apiService)
        val givenSearchedUserResponse = MockTestUtil.createSearchResponse(30)
        val apiCall = successCall(givenSearchedUserResponse)

        // When
        coEvery { apiService.searchForUser(any(), any(), any(), any()) }
            .returns(apiCall)

        // Invoke
        val apiResponseFlow = repository.searchUser("stackoverflow","Gideon","reputation","desc")

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val searchedUsersDataState = apiResponseFlow.first()
        MatcherAssert.assertThat(searchedUsersDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(searchedUsersDataState, CoreMatchers.instanceOf(ApiDataState.Success::class.java))

        val searchedUserResponse = (searchedUsersDataState as ApiDataState.Success).data
        MatcherAssert.assertThat(searchedUserResponse, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(searchedUserResponse.items?.size, CoreMatchers.`is`(
            givenSearchedUserResponse.items?.size
        ))

        coVerify(exactly = 1) { apiService.searchForUser(any(), any(), any(), any()) }
        confirmVerified(apiService)
    }

    @Test
    fun `test searchForUsers() gives empty list of user`() = runBlocking {
        // Given
        repository = StackExchangeCandySpaceRepositoryImpl(stringUtils, apiService)
        val givenSearchedUserResponse = MockTestUtil.createSearchResponse(0)
        val apiCall = successCall(givenSearchedUserResponse)

        // When
        coEvery { apiService.searchForUser(any(), any(), any(),any()) }
            .returns(apiCall)

        // Invoke
        val apiResponseFlow = repository.searchUser("stackoverflow","","reputation","desc")

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val searchedUsersDataState = apiResponseFlow.first()
        MatcherAssert.assertThat(searchedUsersDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(searchedUsersDataState, CoreMatchers.instanceOf(ApiDataState.Success::class.java))

        val searchedUserResponse = (searchedUsersDataState as ApiDataState.Success).data
        MatcherAssert.assertThat(searchedUserResponse, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(searchedUserResponse.items?.size, CoreMatchers.`is`(givenSearchedUserResponse.items?.size))

        coVerify(exactly = 1) { apiService.searchForUser(any(), any(), any(),any())  }
        confirmVerified(apiService)
    }

    @Test
    fun `test searchForUsers() throws exception`() = runBlocking {
        // Given
        repository = StackExchangeCandySpaceRepositoryImpl(stringUtils, apiService)
        val givenMessage = "Test Error Message"
        val exception = Exception(givenMessage)
        val apiResponse = ApiResponse.exception<SearchUserResponse>(exception)

        // When
        coEvery { apiService.searchForUser(any(), any(), any(),any()) }
            .returns(apiResponse)
        coEvery { stringUtils.somethingWentWrong() }
            .returns(givenMessage)

        // Invoke
        val apiResponseFlow = repository.searchUser("stackoverflow","Gideon","reputation","desc")

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(apiResponseFlow.count(), CoreMatchers.`is`(1))

        val apiResponseDataState = apiResponseFlow.first()
        MatcherAssert.assertThat(apiResponseDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(apiResponseDataState, CoreMatchers.instanceOf(ApiDataState.Error::class.java))

        val errorMessage = (apiResponseDataState as ApiDataState.Error).message
        MatcherAssert.assertThat(errorMessage, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(errorMessage, CoreMatchers.equalTo(givenMessage))

        coVerify(atLeast = 1) { apiService.searchForUser(any(), any(), any(),any())}
        confirmVerified(apiService)
    }

    @Test
    fun `test searchForUsers() gives network error`() = runBlocking {
        // Given
        repository = StackExchangeCandySpaceRepositoryImpl(stringUtils, apiService)
        val givenMessage = "Test Error Message"
        val body = givenMessage.toResponseBody("text/html".toMediaTypeOrNull())
        val apiResponse = ApiResponse.error<SearchUserResponse>(Response.error(500, body))

        // When
        coEvery { apiService.searchForUser(any(), any(), any(),any()) }
            .returns(apiResponse)
        coEvery { stringUtils.somethingWentWrong() }
            .returns(givenMessage)

        // Invoke
        val apiResponseFlow = repository.searchUser("stackoverflow","Gideon","reputation","desc")

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(apiResponseFlow.count(), CoreMatchers.`is`(1))

        val apiResponseDataState = apiResponseFlow.first()
        MatcherAssert.assertThat(apiResponseDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(apiResponseDataState, CoreMatchers.instanceOf(ApiDataState.Error::class.java))

        val errorMessage = (apiResponseDataState as ApiDataState.Error).message
        MatcherAssert.assertThat(errorMessage, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(errorMessage, CoreMatchers.equalTo(apiResponse.message()))

        coVerify(atLeast = 1) { apiService.searchForUser(any(), any(), any(),any())  }
        confirmVerified(apiService)
    }
}
