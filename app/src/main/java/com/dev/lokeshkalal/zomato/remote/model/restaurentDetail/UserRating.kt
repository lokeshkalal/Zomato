package com.dev.lokeshkalal.zomato.remote.model.restaurentDetail

import com.google.gson.annotations.SerializedName

data class UserRating(
    @SerializedName("aggregate_rating")
    val aggregateRating: String,
    @SerializedName("has_fake_reviews")
    val hasFakeReviews: Int,
    @SerializedName("rating_color")
    val ratingColor: String,
    @SerializedName("rating_text")
    val ratingText: String,
    @SerializedName("votes")
    val votes: String
)