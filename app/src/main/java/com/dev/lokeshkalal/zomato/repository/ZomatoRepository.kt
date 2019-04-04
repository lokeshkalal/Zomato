package com.dev.lokeshkalal.zomato.repository

import com.dev.lokeshkalal.zomato.remote.model.restaurentDetail.RestaurentDetailResponse
import com.dev.lokeshkalal.zomato.remote.service.ZomatoService
import com.dev.lokeshkalal.zomato.remote.service.ZomatoServiceFactory
import com.dev.lokeshkalal.zomato.repository.model.Restaurent
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import io.reactivex.Single

class ZomatoRepository {

    val zomatoService: ZomatoService

    init {
        zomatoService = ZomatoServiceFactory.makeGithubTrendingService(true)
    }


    fun getRestaurents(category: Int, lat: Double, long: Double, offset: Int): Single<List<Restaurent>> {
        return zomatoService.getRestaurentList(category, lat, long, offset)
            .map {
                it.restaurants.map { restaurant ->
                    Restaurent(
                        restaurant.restaurant.r.resId,
                        restaurant.restaurant.name,
                        restaurant.restaurant.location.localityVerbose,
                        restaurant.restaurant.cuisines,
                        restaurant.restaurant.userRating.aggregateRating,
                        restaurant.restaurant.userRating.ratingColor,
                        restaurant.restaurant.thumb
                    )
                }
            }
    }

    fun getCategories(): Single<List<RestaurentCategory>> {
        return zomatoService.getCategories().map {
            it.categories.map { category ->
                RestaurentCategory(
                    category.categories.id.toString(),
                    category.categories.name
                )
            }
        }

    }


    fun getRestaurentDetail(restaurentID: Int): Single<RestaurentDetailResponse> {
        return zomatoService.getRestaurentDetail(restaurentID)
    }
}