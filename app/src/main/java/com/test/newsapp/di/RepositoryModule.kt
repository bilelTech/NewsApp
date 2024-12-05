package com.test.newsapp.di

import com.test.newsapp.data.remote.NewsApi
import com.test.newsapp.data.repository.NewsRepositoryImpl
import com.test.newsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepositoryImpl(
        newsApi: NewsApi,
        ioDispatcher: CoroutineDispatcher
    ): NewsRepository {
        return NewsRepositoryImpl(newsApi, ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideDispatcherIo(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}