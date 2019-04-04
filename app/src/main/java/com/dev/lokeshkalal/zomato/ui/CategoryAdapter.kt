package com.dev.lokeshkalal.zomato.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.lokeshkalal.zomato.R
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import com.dev.lokeshkalal.zomato.ui.restaurents.RestaurentAdapter

class CategoryAdapter(val homeScreenFragment: HomeScreenFragment) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    companion object {
        const val TAG = "CategoryAdapter"
    }

    var categoryList: List<RestaurentCategory> = mutableListOf()
    var map: HashMap<Int, RestaurentListingViewModel> = hashMapOf()
    var childPositionChildState: MutableMap<Int, ChildRecyclerViewState> = mutableMapOf()
    val viewPool = RecyclerView.RecycledViewPool();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryIdTextView.text = category.id
        holder.categoryNameTextView.text = category.name

        var childRecyclerViewState: ChildRecyclerViewState? = childPositionChildState.get(position)

        if (childRecyclerViewState == null) {
            val layoutManager = LinearLayoutManager(homeScreenFragment.context, LinearLayoutManager.HORIZONTAL, false)
            val restaurentListingViewModel = RestaurentListingViewModel()
            val restaurentAdapter = RestaurentAdapter()
            childRecyclerViewState =
                ChildRecyclerViewState(restaurentAdapter, restaurentListingViewModel, layoutManager)
            holder.recyclerView.layoutManager = childRecyclerViewState.layoutManager
            childPositionChildState.put(position, childRecyclerViewState)
            loadRestaurent(childRecyclerViewState, category.id)
            observeRestaurentDataChanges(childRecyclerViewState)
        }

        holder.recyclerView.adapter = childRecyclerViewState.adapter

        holder.recyclerView.addOnScrollListener(object : TimeLineEndlessRecyclerScrollListener() {
            override fun onLoadMore() {
                //childRecyclerViewState.adapter.showLoadMore()
                loadRestaurent(childRecyclerViewState, category.id)
            }

        })
        (holder.recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
            childRecyclerViewState.currentPosition,
            childRecyclerViewState.offset
        )



        Log.i(TAG, "onBindViewHolder " + childRecyclerViewState.currentPosition + "   " + position)

    }

    private fun observeRestaurentDataChanges(childRecyclerViewState: ChildRecyclerViewState) {
        childRecyclerViewState.viewModel.getRestaurents().observe(homeScreenFragment, Observer {
            childRecyclerViewState.isLoading = false
            childRecyclerViewState.adapter.setData(it)
            childRecyclerViewState.adapter.hideLoadMore()
        })
    }

    fun loadRestaurent(childRecyclerViewState: ChildRecyclerViewState, categortId: String) {
        if (!childRecyclerViewState.isLoading) {
            childRecyclerViewState.isLoading = true
            childRecyclerViewState.adapter.showLoadMore()
            childRecyclerViewState.viewModel.fetchRestaurents(
                categortId.toInt(),
                28.7041,
                77.1025,
                childRecyclerViewState.adapter.restaurentList.count()
            )
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        if (holder is ViewHolder) {
            holder.recyclerView.clearOnScrollListeners()
            val firstVisibleitem =
                (holder.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val item = holder.recyclerView.getChildAt(0)
            val mTopOffset = if (item == null) 0 else item!!.getTop() - holder.recyclerView.getPaddingTop()
            val mLeftOffset = if (item == null) 0 else item!!.getLeft() - holder.recyclerView.getPaddingLeft()
            childPositionChildState.get(holder.adapterPosition)
                ?.let {
                    it.currentPosition = firstVisibleitem
                    it.offset = Math.max(mLeftOffset, mTopOffset)
                }
            Log.i(TAG, "onViewRecycled " + holder.recyclerView.scrollY + "   " + holder.adapterPosition)
        }
    }

    fun setData(data: List<RestaurentCategory>) {
        categoryList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryIdTextView: TextView
        val categoryNameTextView: TextView
        val recyclerView: RecyclerView

        init {
            categoryIdTextView = view.findViewById(R.id.category_id)
            categoryNameTextView = view.findViewById(R.id.category_name)
            recyclerView = view.findViewById(R.id.rest_recyler_view)
            recyclerView.setRecycledViewPool(viewPool)
        }
    }
}