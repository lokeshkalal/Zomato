package com.dev.lokeshkalal.zomato.ui.homeScreen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.lokeshkalal.zomato.R
import com.dev.lokeshkalal.zomato.repository.ZomatoRepository
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import com.dev.lokeshkalal.zomato.ui.model.Location
import com.dev.lokeshkalal.zomato.ui.restaurents.*
import com.dev.lokeshkalal.zomato.ui.state.ResourceState

class CategoryAdapter(
    private val homeScreenFragment: HomeScreenFragment, location: Location,
    private val zomatoRepository: ZomatoRepository
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    companion object {
        const val TAG = "CategoryAdapter"
    }


    var currentLocation: Location

    init {
        currentLocation = location
    }

    private var clickListener: RestaurentClickListener? = null
    private var categoryList: List<RestaurentCategory> = mutableListOf()
    private var childPositionChildState: MutableMap<Int, ChildRecyclerViewState> = mutableMapOf()
    private val viewPool = RecyclerView.RecycledViewPool();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.count()
    }

    fun setClickListener(listener: RestaurentClickListener) {
        clickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindView(holder, position)
    }

    private fun observeRestaurentDataChanges(childRecyclerViewState: ChildRecyclerViewState) {
        childRecyclerViewState.viewModel.getRestaurents().observe(homeScreenFragment, Observer {
            when (it.resourceState) {
                ResourceState.SUCCESS -> {
                    childRecyclerViewState.adapter.setData(it.data ?: emptyList())
                }
                else -> {
                    // handle other  states here
                }
            }
        })
    }

    fun loadRestaurent(childRecyclerViewState: ChildRecyclerViewState, categortId: String) {
        if (childRecyclerViewState.viewModel.shouldFetchMoreData()) {
            childRecyclerViewState.viewModel.fetchRestaurents(
                categortId.toInt(),
                currentLocation.lat,
                currentLocation.long,
                childRecyclerViewState.adapter.restaurentList.count()
            )
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        if (holder is ViewHolder) {
            holder.recyclerView.clearOnScrollListeners()
            val firstVisibleitem =
                (holder.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            childPositionChildState.get(holder.adapterPosition)
                ?.let {
                    it.currentPosition = firstVisibleitem

                }
            Log.i(TAG, "onViewRecycled " + holder.recyclerView.scrollY + "   " + holder.adapterPosition)
        }
    }

    fun setData(data: List<RestaurentCategory>) {
        categoryList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryNameTextView: TextView
        val recyclerView: RecyclerView

        init {
            categoryNameTextView = view.findViewById(R.id.category_name)
            recyclerView = view.findViewById(R.id.rest_recyler_view)
            recyclerView.setRecycledViewPool(viewPool)
        }
    }

    private fun bindView(holder: ViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryNameTextView.text = category.name

        var childRecyclerViewState: ChildRecyclerViewState? = childPositionChildState.get(position)

        if (childRecyclerViewState == null) {
            val restaurentListingViewModel = RestaurentListingViewModel(zomatoRepository)
            val restaurentAdapter = RestaurentAdapter()
            restaurentAdapter.setClickListener(clickListener)
            childRecyclerViewState =
                ChildRecyclerViewState(
                    restaurentAdapter,
                    restaurentListingViewModel
                )
            childPositionChildState.put(position, childRecyclerViewState)
            loadRestaurent(childRecyclerViewState, category.id)
            observeRestaurentDataChanges(childRecyclerViewState)
        }


        holder.recyclerView.adapter = childRecyclerViewState.adapter
        holder.recyclerView.layoutManager =
            LinearLayoutManager(homeScreenFragment.context, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerView.addOnScrollListener(object : LoadMoreRecyclerScrollListener() {
            override fun onLoadMore() {
                loadRestaurent(childRecyclerViewState, category.id)
            }

        })
        (holder.recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
            childRecyclerViewState.currentPosition,
            0
        )

        Log.i(
            TAG,
            "onBindViewHolder " + childRecyclerViewState.currentPosition + "   " + position
        )
    }
}