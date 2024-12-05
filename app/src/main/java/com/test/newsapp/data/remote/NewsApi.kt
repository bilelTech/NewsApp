package com.test.newsapp.data.remote

import com.test.newsapp.BuildConfig
import com.test.newsapp.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") q: String = "Apple",
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsResponse
}