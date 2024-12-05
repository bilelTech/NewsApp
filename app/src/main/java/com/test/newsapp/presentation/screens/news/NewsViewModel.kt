package com.test.newsapp.presentation.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.test.newsapp.domain.usecases.GetNewsUseCase
import com.test.newsapp.presentation.models.NewsUI
import com.test.newsapp.presentation.utils.mapToNewsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {


    private val _newsState: MutableStateFlow<PagingData<NewsUI>> =
        MutableStateFlow(value = PagingData.empty())
    val newsState: StateFlow<PagingData<NewsUI>> get() = _newsState

    init {
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            getNewsUseCase.invoke()
                .distinctUntilChanged()
                .map { newsPagingData ->
                    newsPagingData.map {
                        it.mapToNewsUI()
                    }
                }
                .cachedIn(viewModelScope)
                .collect {
                    _newsState.update { _ ->
                        it
                    }
                }
        }
    }
}