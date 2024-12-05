package com.test.newsapp.di

import com.test.newsapp.domain.repository.NewsRepository
import com.test.newsapp.domain.usecases.GetNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCasesModule {

    @Singleton
    @Provides
    fun provideGetNewsUseCase(newsRepository: NewsRepository): GetNewsUseCase {
        return GetNewsUseCase(newsRepository)
    }
}