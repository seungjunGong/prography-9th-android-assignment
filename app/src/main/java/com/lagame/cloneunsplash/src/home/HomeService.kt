package com.lagame.cloneunsplash.src.home

import android.util.Log
import com.lagame.cloneunsplash.config.ApplicationClass.Companion.sRetrofit
import com.lagame.cloneunsplash.src.home.photos.HomePhotosDTO
import retrofit2.Call
import retrofit2.Response

class HomeService (val homeFragmentInterface: HomeFragmentInterface){

    fun tryGetPhotos(){
        val homeRetrofitInterface = sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getPhotos().enqueue(object : retrofit2.Callback<List<HomePhotosDTO>>{

            override fun onResponse(
                call: Call<List<HomePhotosDTO>>,
                response: Response<List<HomePhotosDTO>>,
            ) {
                homeFragmentInterface.onGetPhotosSuccess(response.body() as List<HomePhotosDTO>)
            }

            override fun onFailure(call: Call<List<HomePhotosDTO>>, t: Throwable) {
                homeFragmentInterface.onGetPhotosFailure(t.message ?: "통신 오류")
            }

        })
    }
}