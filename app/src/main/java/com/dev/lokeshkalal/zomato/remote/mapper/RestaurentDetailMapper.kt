package com.dev.lokeshkalal.zomato.remote.mapper

import com.dev.lokeshkalal.zomato.remote.model.restaurentDetail.RestaurentDetailResponse
import com.dev.lokeshkalal.zomato.repository.model.RestaurentDetail
import javax.inject.Inject

class RestaurentDetailMapper @Inject constructor() : ModelMapper<RestaurentDetailResponse, RestaurentDetail> {
    override fun mapModelFromResponse(model: RestaurentDetailResponse): RestaurentDetail {
        return RestaurentDetail(
            model.name,
            model.cuisines,
            model.userRating.aggregateRating,
            model.userRating.votes,
            model.userRating.ratingColor,
            model.thumb,
            model.location.localityVerbose,
            model.currency,
            model.averageCostForTwo.toString(),
            model.isDeliveringNow == 1
        )
    }
}