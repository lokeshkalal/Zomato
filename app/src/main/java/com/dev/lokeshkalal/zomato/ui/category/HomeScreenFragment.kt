package com.dev.lokeshkalal.zomato.ui.category

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.lokeshkalal.zomato.R
import com.dev.lokeshkalal.zomato.repository.model.Restaurent
import com.dev.lokeshkalal.zomato.repository.model.RestaurentCategory
import com.dev.lokeshkalal.zomato.ui.Location
import com.dev.lokeshkalal.zomato.ui.restaurents.RestaurentAdapter
import com.dev.lokeshkalal.zomato.ui.restaurents.RestaurentListingViewModel
import kotlinx.android.synthetic.main.home_screen_fragment.*

class HomeScreenFragment : Fragment() {

    companion object {
        fun newInstance() = HomeScreenFragment()
    }

    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var restaurentViewModel: RestaurentListingViewModel
    private lateinit var adapter: CategoryAdapter
    private lateinit var restaurentAdapter: RestaurentAdapter
    private var currentLocation = Location("Delhi", 28.7041, 77.1025)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeScreenViewModel::class.java)
        restaurentViewModel = ViewModelProviders.of(this).get(RestaurentListingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.home_screen_fragment, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView();
        ObserverFetchCategoryData()
        viewModel.fetchCatgories()
        setToolBar()


        //setUpListRecyclerView()
        //ObserverRestaurentData()
        //restaurentViewModel.fetchRestaurents(1, 28.7041, 77.1025)
    }

    private fun setToolBar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(currentLocation.name)
        (activity as AppCompatActivity).supportActionBar?.setSubtitle(getString(R.string.your_location))

    }

    private fun setUpRecyclerView() {
        adapter = CategoryAdapter(this, currentLocation)
        categories_recyler_view.layoutManager = LinearLayoutManager(activity)
        categories_recyler_view.adapter = adapter
    }

    private fun ObserverFetchCategoryData() {
        viewModel.getCategories().observe(this, Observer { it?.let { renderData(it) } })
    }

    private fun renderData(categoryList: List<RestaurentCategory>) {
        adapter.setData(categoryList)
        progress_bar.visibility = GONE
        categories_recyler_view.visibility = VISIBLE
    }

    private fun setUpListRecyclerView() {
        restaurentAdapter = RestaurentAdapter()
        categories_recyler_view.layoutManager = LinearLayoutManager(activity)
        categories_recyler_view.adapter = restaurentAdapter
    }

    private fun ObserverRestaurentData() {
        restaurentViewModel.getRestaurents().observe(this, Observer { it?.let { renderRestaurentData(it) } })
    }

    private fun renderRestaurentData(categoryList: List<Restaurent>) {
        restaurentAdapter.setData(categoryList)
        progress_bar.visibility = GONE
        categories_recyler_view.visibility = VISIBLE
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeScreenViewModel::class.java)
    }

}
