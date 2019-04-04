package com.dev.lokeshkalal.zomato.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.lokeshkalal.zomato.ui.restaurents.RestaurentAdapter

class ChildRecyclerViewState(
    val adapter: RestaurentAdapter,
    val viewModel: RestaurentListingViewModel,
    val layoutManager: LinearLayoutManager

) {
    var currentPosition: Int = 0
    var offset = 0
    var isLoading = false;

}