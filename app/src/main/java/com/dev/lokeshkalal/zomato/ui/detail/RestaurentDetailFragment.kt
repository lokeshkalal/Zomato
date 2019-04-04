package com.dev.lokeshkalal.zomato.ui.detail

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dev.lokeshkalal.zomato.R
import com.dev.lokeshkalal.zomato.remote.model.restaurentDetail.RestaurentDetailResponse
import kotlinx.android.synthetic.main.restaurent_detail_fragment.*

class RestaurentDetailFragment : Fragment() {

    companion object {

        fun newInstance(restaurentId: Int): RestaurentDetailFragment {
            val bundle = Bundle()
            bundle.putInt(RestaurentDetail.ARG_RESTATURENT_ID, restaurentId);
            val fragment = RestaurentDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: RestaurentDetailViewModel
    private var restaurentId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restaurentId = arguments?.getInt(RestaurentDetail.ARG_RESTATURENT_ID, 0)!!
        viewModel = ViewModelProviders.of(this).get(RestaurentDetailViewModel::class.java)
        viewModel.fetchRestaurentDetail(restaurentId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.restaurent_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observerRestaurentDetailData()

    }

    private fun observerRestaurentDetailData() {
        viewModel.getRestaurentDetail().observe(this, Observer { it?.let { renderData(it) } })
    }

    private fun renderData(restaurentDetailResponse: RestaurentDetailResponse) {
        progress_bar.visibility = View.GONE
        restaurantName.text = restaurentDetailResponse.name
        toolbar.setNavigationIcon(R.drawable.back_button)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        Glide.with(this).load(Uri.parse(restaurentDetailResponse.thumb)).centerCrop().into(top_thumb)
        restaurantCuisines.text = restaurentDetailResponse.cuisines
        restaurantRating.text = restaurentDetailResponse.userRating.aggregateRating
        restaurantRating.background.colorFilter = PorterDuffColorFilter(Color.parseColor("#"+restaurentDetailResponse.userRating.ratingColor), PorterDuff.Mode.SRC)
        restaurantReviews.text = restaurentDetailResponse.userRating.votes + " reviews"
        restaurantReviews.setTextColor(Color.parseColor("#"+restaurentDetailResponse.userRating.ratingColor))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}
