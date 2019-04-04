package com.dev.lokeshkalal.zomato.remote.model

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)