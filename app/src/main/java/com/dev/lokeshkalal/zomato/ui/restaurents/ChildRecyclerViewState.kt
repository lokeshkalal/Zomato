package com.dev.lokeshkalal.zomato.ui.restaurents

import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.lokeshkalal.zomato.ui.restaurents.RestaurentAdapter
import com.dev.lokeshkalal.zomato.ui.restaurents.RestaurentListingViewModel

class ChildRecyclerViewState(
    val adapter: RestaurentAdapter,
    val viewModel: RestaurentListingViewModel,
    val layoutManager: LinearLayoutManager

) {
    var currentPosition: Int = 0
    var offset = 0
    var isLoading = false;

}