package com.gideondev.stackcandyspace.dataApiCall.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gideondev.stackcandyspace.MainCoroutinesRule
import com.gideondev.stackcandyspace.dataApiCall.ApiDataState
import com.gideondev.stackcandyspace.dataApiCall.remote.responses.SearchUserResponse
import com.gideondev.stackcandyspace.test_common.MockTestUtil
import com.gideondev.stackcandyspace.uiState.ContentState
import com.gideondev.stackcandyspace.uiState.SearchUiState
import com.gideondev.stackcandyspace.usecases.SearchUserUseCase
import com.gideondev.stackcandyspace.viewModel.SearchUserViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StackExchangeCandySpaceViewModelTest {

    // Subject under test
    private lateinit var viewModel: SearchUserViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var searchUserUsecase: SearchUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test when SearchUserViewModel is initialized, user search response is returned that contain found users`() = runBlocking {
        // Given
        val givenSearchedUser = MockTestUtil.createSearchResponse(30)
        val uiObserver = mockk<Observer<SearchUiState>>(relaxed = true)
        val searchedUsersObserver = mockk<Observer<SearchUserResponse>>(relaxed = true)

        // When
        coEvery { searchUserUsecase.invoke(any(), any(), any(),any()) }
            .returns(flowOf(ApiDataState.success(givenSearchedUser)))

        // Invoke
        viewModel = SearchUserViewModel(searchUserUsecase)
        viewModel.testSearchUserMethod()
        viewModel.uiStateLiveData.observeForever(uiObserver)
        viewModel.searchUserResponseLiveData.observeForever(searchedUsersObserver)

        // Then
        coVerify(exactly = 1) { searchUserUsecase.invoke("Gideon") }
        verify { uiObserver.onChanged(match { it == ContentState }) }
        verify { searchedUsersObserver.onChanged(match { it.items?.size  == givenSearchedUser.items?.size }) }
    }
}
