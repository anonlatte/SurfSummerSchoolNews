package com.anonlatte.natgeo.ui.home.interactor

import com.anonlatte.natgeo.data.model.UserInfo
import com.anonlatte.natgeo.data.model.article.ArticlesData
import com.anonlatte.natgeo.data.network.ErrorState
import com.anonlatte.natgeo.data.network.RequestState
import com.anonlatte.natgeo.data.repository.MainRepository
import com.anonlatte.natgeo.ui.home.state.NewsUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class HomeInteractorImpl constructor(
    private val mainRepository: MainRepository
) : HomeInteractor {

    private val deviceInfo = UserInfo(
        language = Locale.getDefault().isO3Language.take(2),
        country = Locale.getDefault().isO3Country.take(2)
    )

    override suspend fun getTopHeadlines(): Flow<NewsUiState> {
        return buildRequestFlow {
            mainRepository.getTopHeadlines(
                language = deviceInfo.language,
                country = deviceInfo.country
            )
        }
    }

    override suspend fun getArticles(
        query: String
    ): Flow<NewsUiState> {
        return buildRequestFlow {
            mainRepository.getArticles(
                query = query,
                language = deviceInfo.language
            )
        }
    }

    private fun buildRequestFlow(
        request: suspend () -> RequestState<ArticlesData>
    ): Flow<NewsUiState> {
        return flow {
            emit(NewsUiState.Loading)
            val newsUiState = when (val requestState = request()) {
                is RequestState.Success -> NewsUiState.Success(requestState.value.articles)
                else -> NewsUiState.Error(requestState as ErrorState)
            }
            emit(newsUiState)
        }
    }
}