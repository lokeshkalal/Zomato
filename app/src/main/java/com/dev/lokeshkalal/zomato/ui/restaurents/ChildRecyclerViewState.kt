package com.dev.lokeshkalal.zomato.ui.restaurents

import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.lokeshkalal.zomato.ui.restaurents.RestaurentAdapter
import com.dev.lokeshkalal.zomato.ui.restaurents.RestaurentListingViewModel

class ChildRecyclerViewState(
    val adapter: RestaurentAdapter,
    val viewModel: RestaurentListingViewModel

) {
    var currentPosition: Int = 0
}