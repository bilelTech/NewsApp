package com.test.newsapp.utils.network

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.Request

class NetworkMonitorInterceptor @Inject constructor(
    private val liveNetworkMonitor: NetworkMonitor
) : Interceptor {


    @Throws(NoNetworkException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        if (liveNetworkMonitor.isConnected()) {
            return chain.proceed(request)
        } else {
            throw NoNetworkException("Network Error")
        }

    }
}