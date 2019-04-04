package com.dev.lokeshkalal.zomato.remote.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("categories")
    val categories: Categories
)