package com.test.newsapp.domain.usecases

import com.test.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke() = newsRepository.getNews()
}