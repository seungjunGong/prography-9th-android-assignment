package com.lagame.cloneunsplash.src.random

import com.lagame.cloneunsplash.config.ApplicationClass
import com.lagame.cloneunsplash.src.home.HomeFragmentInterface
import com.lagame.cloneunsplash.src.home.HomeRetrofitInterface
import com.lagame.cloneunsplash.src.home.photos.HomePhotosDTO
import retrofit2.Call
import retrofit2.Response

class RandomPhotoService (val randomPhotoFragmentInterface: RandomPhotoFragmentInterface){

    fun tryGetRandomPhotos(){
        val randomPhotoRetrofitInterface = ApplicationClass.sRetrofit.create(RandomPhotoRetrofitInterface::class.java)
        randomPhotoRetrofitInterface.getRandomPhotos().enqueue(object : retrofit2.Callback<HomePhotosDTO>{

            override fun onResponse(
                call: Call<HomePhotosDTO>,
                response: Response<HomePhotosDTO>,
            ) {
                randomPhotoFragmentInterface.onGetRandomPhotosSuccess(response.body() as HomePhotosDTO)
            }

            override fun onFailure(call: Call<HomePhotosDTO>, t: Throwable) {
                randomPhotoFragmentInterface.onGetRandomPhotosFailure(t.message ?:"통신 오류")
            }

        })
    }
}