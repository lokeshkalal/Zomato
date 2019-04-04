package com.dev.lokeshkalal.zomato.ui.restaurentDetail

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dev.lokeshkalal.zomato.R
import com.dev.lokeshkalal.zomato.Utils.Util
import com.dev.lokeshkalal.zomato.injection.ViewModelFactory
import com.dev.lokeshkalal.zomato.remote.model.restaurentDetail.RestaurentDetailResponse
import com.dev.lokeshkalal.zomato.repository.model.RestaurentDetail
import com.dev.lokeshkalal.zomato.ui.state.Resource
import com.dev.lokeshkalal.zomato.ui.state.ResourceState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.restaurent_detail_fragment.*
import javax.inject.Inject
import kotlin.random.Random

class RestaurentDetailFragment : Fragment() {

    companion object {

        fun newInstance(restaurentId: Int): RestaurentDetailFragment {
            val bundle = Bundle()
            bundle.putInt(RestaurentDetailActivity.ARG_RESTATURENT_ID, restaurentId);
            val fragment = RestaurentDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: RestaurentDetailViewModel
    private var restaurentId: Int = 0
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RestaurentDetailViewModel::class.java)
        restaurentId = arguments?.getInt(RestaurentDetailActivity.ARG_RESTATURENT_ID, 0)!!
        viewModel.fetchRestaurentDetail(restaurentId)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.restaurent_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRetryButton()
        observerRestaurentDetailData()
        setUpToolBar()

    }

    private fun setUpToolBar() {
        toolbar.setNavigationIcon(R.drawable.back_button)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    private fun setUpRetryButton() {
        retry.setOnClickListener { viewModel.fetchRestaurentDetail(restaurentId) }
    }

    private fun observerRestaurentDetailData() {
        viewModel.getRestaurentDetail().observe(this, Observer { renderData(it) })
    }


    private fun renderData(restaurentDetailResource: Resource<RestaurentDetail>) {
        when (restaurentDetailResource.resourceState) {
            ResourceState.LOADING -> {
                showLoadingScreen();
            }
            ResourceState.SUCCESS -> {
                showData(restaurentDetailResource.data)
            }
            ResourceState.ERROR -> {
                showErrorScreen()
            }
        }
    }


    private fun showData(restaurentDetail: RestaurentDetail?) {
        restaurentDetail?.let {
            //adapter.setData(data)
            progress_bar.visibility = View.GONE
            retry.visibility = View.GONE
            scroll_view_parent.visibility = View.VISIBLE

            setRestaurentBasicInfo(restaurentDetail)
            loadBanner(restaurentDetail.bannerUrl)
            setAddress(restaurentDetail.address)
            setClosingTime()
            setCostForTwo(restaurentDetail.currency, restaurentDetail.costForTwo)
            setDeliveryInfo(restaurentDetail.isDeliveringNow)

        }

    }

    private fun setDeliveryInfo(isDeliveringNow: Boolean) {
        if (isDeliveringNow) {
            delivery_info.text = getString(R.string.delivering_now)
        } else {
            delivery_info.text = getString(R.string.closed_for_delivery)
        }

    }

    private fun setCostForTwo(currency: String, averageCostForTwo: String) {
        average_cost.text = currency + " " + averageCostForTwo
    }

    private fun setClosingTime() {
        closes_in.text = "Closes in ${Util.formattedTime(Random(500).nextInt(1, 400))}"
    }

    private fun setAddress(addressString: String) {
        address.text = addressString
    }

    private fun setRestaurentBasicInfo(restaurentDetail: RestaurentDetail) {
        restaurant_reviews.setTextColor(Color.parseColor("#" + restaurentDetail.ratingColor))
        restaurant_reviews.text = "${restaurentDetail.votes} reviews"
        restaurant_name.text = restaurentDetail.name
        restaurant_cuisines.text = restaurentDetail.cuisine
        restaurant_rating.text = restaurentDetail.rating
        restaurant_rating.background.colorFilter = PorterDuffColorFilter(
            Color.parseColor("#" + restaurentDetail.ratingColor),
            PorterDuff.Mode.SRC
        )
    }

    private fun loadBanner(url: String) {
        Glide.with(this).load(Uri.parse(url)).centerCrop().into(banner)
    }

    private fun showErrorScreen() {
        progress_bar.visibility = View.GONE
        retry.visibility = View.VISIBLE
        scroll_view_parent.visibility = View.GONE

    }

    private fun showLoadingScreen() {
        progress_bar.visibility = View.VISIBLE
        retry.visibility = View.GONE
        scroll_view_parent.visibility = View.GONE


    }


}
