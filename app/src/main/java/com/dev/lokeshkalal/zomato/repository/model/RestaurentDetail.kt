package com.dev.lokeshkalal.zomato.repository.model

import java.util.*

class RestaurentDetail(
    val name: String,
    val cuisine: String,
    val rating: String,
    val votes: String,
    val ratingColor: String,
    val bannerUrl: String,
    val address: String,
    val currency: String,
    val costForTwo: String,
    val isDeliveringNow: Boolean
)