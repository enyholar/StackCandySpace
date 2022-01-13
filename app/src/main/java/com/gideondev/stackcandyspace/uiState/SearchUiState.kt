package com.gideondev.stackcandyspace.uiState

sealed class SearchUiState

object LoadingState : SearchUiState()
object ContentState : SearchUiState()
class ErrorState(val message: String) : SearchUiState()
