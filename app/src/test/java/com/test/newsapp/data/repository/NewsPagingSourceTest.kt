package com.test.newsapp.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.test.newsapp.data.models.NewsResponse
import com.test.newsapp.data.pagination.NewsPagingSource
import com.test.newsapp.data.remote.NewsApi
import com.test.newsapp.data.utils.Constants
import com.test.newsapp.utils.Utils.createFakeArticleDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class NewsPagingSourceTest {


    private val api: NewsApi = mockk()
    private lateinit var pagingSource: NewsPagingSource

    @Before
    fun setup() {
        pagingSource = NewsPagingSource(api)
    }

    @Test
    fun `load returns page when successful`() = runTest {
        // GIVEN
        val newsArticles = listOf(createFakeArticleDto())
        val mockResponse = NewsResponse(articles = newsArticles, "ok", 3)
        coEvery {
            api.getNews("Apple", "en", 1, Constants.MAX_PAGE_SIZE)
        }.returns(mockResponse)

        // WHEN
        val pager = TestPager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, enablePlaceholders = false),
            pagingSource
        )
        val result = pager.refresh()


        // THEN
        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page

        assertEquals(newsArticles.size, page.data.size)
        assertEquals(newsArticles[0].title, page.data[0].title)
        assertEquals(newsArticles[0].author, page.data[0].author)
        assertEquals(newsArticles[0].description, page.data[0].description)
        assertEquals(newsArticles[0].content, page.data[0].content)
        assertEquals(newsArticles[0].url, page.data[0].url)
        assertEquals(null, page.prevKey)
        assertEquals(2, page.nextKey)
    }

    @Test
    fun `load returns empty page when articles are empty`() = runTest {
        // GIVEN
        val mockResponse = NewsResponse(articles = emptyList(), "ok", 3)

        coEvery {
            api.getNews("Apple", "en", 1, Constants.MAX_PAGE_SIZE)
        }.returns(mockResponse)

        // WHEN
        val pager = TestPager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, enablePlaceholders = false),
            pagingSource
        )

        val result = pager.refresh()
        // THEN
        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertTrue(page.data.isEmpty())
        assertEquals(null, page.prevKey)
        assertEquals(null, page.nextKey)
    }

    @Test
    fun `load returns error when HttpException occurs`() = runTest {
        // GIVEN

        val mockedHttpException = mockk<HttpException>()
        coEvery {
            api.getNews("Apple", "en", 1, Constants.MAX_PAGE_SIZE)
        }.throws(mockedHttpException)

        // WHEN
        val pager = TestPager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, enablePlaceholders = false),
            pagingSource
        )

        val result = pager.refresh()


        // THEN
        assertTrue(result is PagingSource.LoadResult.Error)

    }
}