package com.lagame.cloneunsplash.src.home

import com.lagame.cloneunsplash.src.home.photos.HomePhotosDTO
import retrofit2.Call
import retrofit2.http.GET

interface HomeRetrofitInterface {

    @GET("/photos")
    fun getPhotos() : Call<List<HomePhotosDTO>>
}