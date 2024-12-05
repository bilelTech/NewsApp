package com.test.newsapp.data.remote

import com.test.newsapp.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class NewsApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: NewsApi

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(NewsApi::class.java)
    }

    @Test
    fun `should return news list on success`() = runBlocking {
        // GIVEN
        enqueueResponse("news.json")
        // WHEN
        val newsResponse = service.getNews("Apple","fr",1,10, BuildConfig.API_KEY)

        // THEN
        Assert.assertEquals(20693, newsResponse.totalResults)
        Assert.assertEquals(3,newsResponse.articles?.size)
        Assert.assertEquals("Black Friday Apple deals 2024: The best Apple sales on iPads, AirPods, Apple Watches and MacBooks", newsResponse.articles?.get(0)?.title)
        Assert.assertEquals("2024-11-19T16:51:43Z", newsResponse.articles?.get(0)?.publishedAt)
        Assert.assertEquals("Amy Skorheim", newsResponse.articles?.get(0)?.author)
        Assert.assertEquals("https://consent.yahoo.com/v2/collectConsent?sessionId=1_cc-session_5b50f93c-f2e5-4f39-87c2-8667512a5599", newsResponse.articles?.get(0)?.url)
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()

        val mockResponse = MockResponse()

        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }

        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

}