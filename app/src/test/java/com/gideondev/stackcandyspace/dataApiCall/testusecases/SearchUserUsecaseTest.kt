package com.gideondev.stackcandyspace.dataApiCall.testusecases
import com.gideondev.stackcandyspace.dataApiCall.ApiDataState
import com.gideondev.stackcandyspace.repository.StackExchangeCandySpaceRepository
import com.gideondev.stackcandyspace.test_common.MockTestUtil
import com.gideondev.stackcandyspace.usecases.SearchUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SearchUserUsecaseTest {

    @MockK
    private lateinit var repository: StackExchangeCandySpaceRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test invoking SearchPhotosUsecase gives list of users that matched searched value`() = runBlocking {
        // Given
        val usecase = SearchUserUseCase(repository)
        val givenSearchUserResponse = MockTestUtil.createSearchResponse(30)

        // When
        coEvery { repository.searchUser(any(), any(), any(),any()) }
            .returns(flowOf(ApiDataState.success(givenSearchUserResponse)))

        // Invoke
        val searchUsersResponseFlow = usecase("Gideon")

        // Then
        MatcherAssert.assertThat(searchUsersResponseFlow, CoreMatchers.notNullValue())

        val searchUserResponseDataState = searchUsersResponseFlow.first()
        MatcherAssert.assertThat(searchUserResponseDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(searchUserResponseDataState, CoreMatchers.instanceOf(ApiDataState.Success::class.java))

        val searchUsersResponse = (searchUserResponseDataState as ApiDataState.Success).data
        MatcherAssert.assertThat(searchUsersResponse, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(searchUsersResponse.items?.size, CoreMatchers.`is`(givenSearchUserResponse.items?.size))
    }
}
