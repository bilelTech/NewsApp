package com.test.newsapp.presentation.viewmodels

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.test.newsapp.domain.usecases.GetNewsUseCase
import com.test.newsapp.presentation.screens.news.NewsViewModel
import com.test.newsapp.utils.Utils.createFakeNews
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class NewsViewModelTest {


    private val getNewsUseCase: GetNewsUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getNews updates newsState`() = runTest {
        val newsList = listOf(
            createFakeNews()
        )
        // Given
        val pagingData = PagingData.from(newsList)

        coEvery { getNewsUseCase.invoke() } returns flow {
            emit(pagingData)
        }
        val viewModel = NewsViewModel(getNewsUseCase)
        // THEN
        viewModel.newsState.test {
            awaitItem()
            advanceUntilIdle()
            val items =  awaitItem()
            advanceUntilIdle()
            val list = flowOf(items).asSnapshot()
            advanceUntilIdle()
            assertEquals(newsList[0].title, list[0].title)
        }
    }
}
