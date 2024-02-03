package com.lagame.cloneunsplash.src.home.photos

import com.google.gson.annotations.SerializedName

data class HomePhotosDTO(
    @SerializedName("urls")
    val urls: HomePhotosDTOUrls,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("width")
    val width: Int?
)

data class HomePhotosDTOUrls(
    @SerializedName("raw")
    val raw: String
)