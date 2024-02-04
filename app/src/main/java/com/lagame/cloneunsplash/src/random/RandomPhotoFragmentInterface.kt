package com.lagame.cloneunsplash.src.random

import com.lagame.cloneunsplash.src.home.photos.HomePhotosDTO

interface RandomPhotoFragmentInterface {
    fun onGetRandomPhotosSuccess(response: HomePhotosDTO)
    fun onGetRandomPhotosFailure(message: String)
}
