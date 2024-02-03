package com.lagame.cloneunsplash.src.home

import com.lagame.cloneunsplash.src.home.photos.HomePhotosDTO

interface HomeFragmentInterface {

    fun onGetPhotosSuccess(response: List<HomePhotosDTO>)

    fun onGetPhotosFailure(message: String)
}