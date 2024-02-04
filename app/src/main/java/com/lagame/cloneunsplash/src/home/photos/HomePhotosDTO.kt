package com.lagame.cloneunsplash.src.home.photos

import com.google.gson.annotations.SerializedName

data class HomePhotosDTO(
    @SerializedName("urls")
    val urls: HomePhotosDTOUrls,
)

data class HomePhotosDTOUrls(
    @SerializedName("raw")
    val raw: String
)