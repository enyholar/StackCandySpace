package com.gideondev.stackcandyspace.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gideondev.stackcandyspace.dataApiCall.ApiDataState
import com.gideondev.stackcandyspace.dataApiCall.remote.responses.SearchUserResponse
import com.gideondev.stackcandyspace.uiState.*
import com.gideondev.stackcandyspace.usecases.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val searchForUserUsecase: SearchUserUseCase
) : ViewModel() {

    private var _uiState = MutableLiveData<SearchUiState>()
    var uiStateLiveData: LiveData<SearchUiState> = _uiState

    private var _searchUserResponse = MutableLiveData<SearchUserResponse>()
    var searchUserResponseLiveData: LiveData<SearchUserResponse> = _searchUserResponse
    private var searchQuery: String = ""

    fun retry() {
        searchUser(searchQuery)
    }

    fun testSearchUserMethod() {
        searchUser("")
    }

    fun searchForUser(query: String) {
        searchQuery = query
        searchUser(query)
    }


    private fun searchUser(query: String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            searchForUserUsecase(query).collect { dataState ->
                when (dataState) {
                    is ApiDataState.Success -> {
                            _uiState.postValue(ContentState)
                            _searchUserResponse.postValue(dataState.data!!)
                    }

                    is ApiDataState.Error -> {
                        _uiState.postValue(ErrorState(dataState.message))
                    }
                }
            }
        }
    }
}
