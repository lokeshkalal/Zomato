package com.dev.lokeshkalal.zomato.remote.model

import com.google.gson.annotations.SerializedName

data class RestaurentListingResponse(
    @SerializedName("restaurants")
    val restaurants: List<Restaurant>
)