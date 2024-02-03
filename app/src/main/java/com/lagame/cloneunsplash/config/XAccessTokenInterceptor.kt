package com.lagame.cloneunsplash.config

import com.lagame.cloneunsplash.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
            builder.addHeader("Authorization", X_ACCESS_TOKEN)
        return chain.proceed(builder.build())
    }
}