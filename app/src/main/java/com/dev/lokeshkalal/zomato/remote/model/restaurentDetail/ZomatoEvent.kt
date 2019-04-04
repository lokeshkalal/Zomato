package com.dev.lokeshkalal.zomato.remote.model.restaurentDetail

import com.google.gson.annotations.SerializedName

data class ZomatoEvent(
    @SerializedName("event")
    val event: Event
)