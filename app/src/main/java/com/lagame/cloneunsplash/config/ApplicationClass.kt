package com.lagame.cloneunsplash.config

import android.app.Application
import android.content.SharedPreferences
import com.lagame.cloneunsplash.src.home.bookmark.BookMarkDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {

    val API_URL = "https://api.unsplash.com/"

    companion object {

        lateinit var sSharedPreferences: SharedPreferences
        lateinit var sRetrofit: Retrofit

        // ACCESS Token Header 키 값
        // please hide code
        const val X_ACCESS_TOKEN = "Client-ID wRXcWIir-9YYKluXfnbMHfTCiEq8YQuB38uY7l_ndyE"

        // bookmark variables
        var bookmarks = ArrayList<BookMarkDTO>()
    }

    override fun onCreate() {
        (super.onCreate())
        sSharedPreferences =
            applicationContext.getSharedPreferences("INSTAGRAM_TEMPLATE_APP", MODE_PRIVATE)

        // 레트로핏 인스턴스 생성
        initRetrofitInstance()
    }

    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // 자동 헤더 전송
            .build()

        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}