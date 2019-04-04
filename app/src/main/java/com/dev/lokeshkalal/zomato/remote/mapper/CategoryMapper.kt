package com.dev.lokeshkalal.zomato.remote.mapper

import com.dev.lokeshkalal.zomato.remote.model.Categories
import com.dev.lokeshkalal.zomato.remote.model.CategoryResponse
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import javax.inject.Inject

class CategoryMapper @Inject constructor() : ModelMapper<Categories, RestaurentCategory> {
    override fun mapModelFromResponse(model: Categories): RestaurentCategory {
        return RestaurentCategory(
            model.id.toString(),
            model.name
        )
    }
}