package com.dev.lokeshkalal.zomato.remote.model.restaurentDetail

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("photo")
    val photo: PhotoX
)