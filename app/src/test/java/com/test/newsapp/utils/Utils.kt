package com.test.newsapp.utils

import com.test.newsapp.data.models.ArticleDto
import com.test.newsapp.domain.models.News
import java.time.LocalDateTime

object Utils {

    fun createFakeArticleDto() = ArticleDto(
        author = "John Doe",
        content = "This is a test content.",
        description = "This is a test description.",
        publishedAt = "2024-12-04T10:00:00Z",
        source = null,
        title = "Test Title",
        url = "https://test.com/article",
        urlToImage = "https://test.com/image.jpg"
    )

    fun createFakeNews(expectedDateTime: LocalDateTime ?= null) = News(
        author = "John Doe",
        content = "This is a test content.",
        description = "This is a test description.",
        publishedAt = expectedDateTime,
        title = "Test Title",
        url = "https://test.com/article",
        urlToImage = "https://test.com/image.jpg"
    )

    fun createNullArticleDto() = ArticleDto(
        author = null,
        content = null,
        description = null,
        publishedAt = null,
        source = null,
        title = null,
        url = null,
        urlToImage = null
    )
}