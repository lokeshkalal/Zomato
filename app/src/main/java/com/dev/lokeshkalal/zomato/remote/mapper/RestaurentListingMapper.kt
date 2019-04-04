package com.dev.lokeshkalal.zomato.remote.mapper

import com.dev.lokeshkalal.zomato.remote.model.Restaurant
import com.dev.lokeshkalal.zomato.remote.model.RestaurantX
import com.dev.lokeshkalal.zomato.repository.model.Restaurent
import javax.inject.Inject

class RestaurentListingMapper @Inject constructor() : ModelMapper<RestaurantX, Restaurent> {
    override fun mapModelFromResponse(model: RestaurantX): Restaurent {
        return Restaurent(
            model.r.resId,
            model.name,
            model.location.localityVerbose,
            model.cuisines,
            model.userRating.aggregateRating,
            model.userRating.ratingColor,
            model.thumb
        )
    }
}
