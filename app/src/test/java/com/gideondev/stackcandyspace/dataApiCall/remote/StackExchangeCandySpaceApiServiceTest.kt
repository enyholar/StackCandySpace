package com.gideondev.stackcandyspace.dataApiCall.remote

import com.gideondev.stackcandyspace.MainCoroutinesRule
import com.gideondev.stackcandyspace.dataApiCall.remote.api.ApiAbstract
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class StackExchangeCandySpaceApiServiceTest : ApiAbstract<StackExchangeCandySpaceApiService>() {

    private lateinit var apiService: StackExchangeCandySpaceApiService

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        apiService = createService(StackExchangeCandySpaceApiService::class.java)
    }

    @After
    fun tearDown() {
    }

    @Throws(IOException::class)
    @Test
    fun `test searchForUsers() returns response that contain list of users found`() = runBlocking {
        // Given
        enqueueResponse("/search_users_response.json")

        // Invoke
        val response = apiService.searchForUser("stackoverflow","Gideon","reputation","desc")
        val responseBody = requireNotNull((response as ApiResponse.ApiSuccessResponse).data)
        mockWebServer.takeRequest()

        // Then
        assertThat(responseBody.hasMore, `is`(true))
        assertThat(responseBody.quotaMax, `is`(300))
        assertThat(responseBody.items?.size, `is`(30))

        assertThat(responseBody.items?.get(0)?.userId, `is`(158779))
        assertThat(responseBody.items?.get(0)?.reputation, `is`(46050))
        assertThat(responseBody.items?.get(0)?.displayName, `is`("Brian Gideon"))
        assertThat(responseBody.items?.get(0)?.location, `is`("St Louis, MO"))
        assertThat(responseBody.items?.get(0)?.userType, `is`("registered"))
    }
}
