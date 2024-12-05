package com.test.newsapp.domain.repository

import androidx.paging.PagingData
import com.test.newsapp.domain.models.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<PagingData<News>>
}