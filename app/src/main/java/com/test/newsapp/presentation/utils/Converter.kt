package com.test.newsapp.presentation.utils

import com.test.newsapp.domain.models.News
import com.test.newsapp.presentation.models.NewsUI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun News.mapToNewsUI() = NewsUI(
    author = this.author,
    content = this.content,
    description = this.description,
    publishedAt = this.publishedAt?.convertDateToString(),
    title = this.title,
    url = this.url,
    urlToImage = this.urlToImage
)

fun LocalDateTime.convertDateToString(): String =
    this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
