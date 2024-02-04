package com.lagame.cloneunsplash.src.random

import com.lagame.cloneunsplash.src.home.photos.HomePhotosDTO
import retrofit2.Call
import retrofit2.http.GET

interface RandomPhotoRetrofitInterface {

    @GET("/photos/random")
    fun getRandomPhotos(): Call<HomePhotosDTO>
}