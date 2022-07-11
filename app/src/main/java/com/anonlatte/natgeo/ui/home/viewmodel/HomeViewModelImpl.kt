package com.anonlatte.natgeo.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonlatte.natgeo.ui.home.interactor.HomeInteractor
import com.anonlatte.natgeo.ui.home.state.NewsUiEvent
import com.anonlatte.natgeo.ui.home.state.NewsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModelImpl constructor(
    private val interactor: HomeInteractor
) : ViewModel(), HomeViewModel {

    override val uiState = MutableStateFlow<NewsUiState>(NewsUiState.Empty)
    override val uiEvent = MutableStateFlow<NewsUiEvent>(NewsUiEvent.Idle)

    override fun getNews() {
        viewModelScope.launch {
            interactor.getArticles("scientist").collect {
                when (it) {
                    is NewsUiState.Success -> uiState.emit(it)
                    NewsUiState.Empty -> {}
                    is NewsUiState.Error -> {}
                    NewsUiState.Loading -> {}
                }
            }
        }
    }
}