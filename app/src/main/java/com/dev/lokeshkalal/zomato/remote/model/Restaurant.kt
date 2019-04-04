package com.dev.lokeshkalal.zomato.remote.model

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("restaurant")
    val restaurant: RestaurantX
)