package com.gideondev.stackcandyspace.uiState

sealed class SearchUiState

object LoadingState : SearchUiState()
object LoadingNextPageState : SearchUiState()
object ContentState : SearchUiState()
object ContentNextPageState : SearchUiState()
object EmptyState : SearchUiState()
class ErrorState(val message: String) : SearchUiState()
class ErrorNextPageState(val message: String) : SearchUiState()
