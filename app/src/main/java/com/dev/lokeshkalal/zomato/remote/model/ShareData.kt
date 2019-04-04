package com.dev.lokeshkalal.zomato.remote.model

import com.google.gson.annotations.SerializedName

data class ShareData(
    @SerializedName("should_show")
    val shouldShow: Int
)