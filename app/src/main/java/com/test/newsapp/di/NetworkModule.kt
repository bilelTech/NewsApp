package com.test.newsapp.di

import android.content.Context
import com.test.newsapp.BuildConfig
import com.test.newsapp.data.remote.NewsApi
import com.test.newsapp.utils.network.LiveNetworkMonitor
import com.test.newsapp.utils.network.NetworkMonitor
import com.test.newsapp.utils.network.NetworkMonitorInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext appContext: Context
    ): NetworkMonitor {
        return LiveNetworkMonitor(appContext)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: Interceptor,
        liveNetworkMonitor: NetworkMonitor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(NetworkMonitorInterceptor(liveNetworkMonitor))
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        val client = okHttpClient.newBuilder().build()

        return Retrofit.Builder()
            .baseUrl(
                BuildConfig.BASE_URL
            )
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideRemoteApi(retrofit: Retrofit.Builder): NewsApi {
        return retrofit
            .build()
            .create(NewsApi::class.java)
    }
}