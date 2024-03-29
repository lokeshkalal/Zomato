package com.dev.lokeshkalal.zomato.ui.restaurents

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dev.lokeshkalal.zomato.R
import com.dev.lokeshkalal.zomato.repository.model.Restaurent


class RestaurentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class VIEW_TYPES {
        VIEW_TYPE_RESTAURENT, VIEW_TYPE_LOADER
    }

    var showLoadMore = true; // hacky way but want to create common model interface for now
    var restaurentList: List<Restaurent> = mutableListOf()
    private var clickListener: RestaurentClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (VIEW_TYPES.values()[viewType]) {

            VIEW_TYPES.VIEW_TYPE_RESTAURENT -> {
                return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.restaurent_item, parent, false))
            }
            VIEW_TYPES.VIEW_TYPE_LOADER -> {
                return LoadingViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.load_more_item,
                        parent,
                        false
                    )
                )
            }
        }

    }

    override fun getItemCount(): Int {
        if (showLoadMore) {
            return restaurentList.count() + 1;
        } else {
            return restaurentList.count()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (showLoadMore && position == restaurentList.count()) {
            return VIEW_TYPES.VIEW_TYPE_LOADER.ordinal
        }
        return VIEW_TYPES.VIEW_TYPE_RESTAURENT.ordinal

    }

    fun showLoadMore() {
        if (!showLoadMore) {
            showLoadMore = true
            notifyItemInserted(restaurentList.count())
        }
    }

    fun hideLoadMore() {
        if (showLoadMore) {
            showLoadMore = false
            notifyItemRemoved(restaurentList.count())
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(restaurentList[position], clickListener)
            }

            is LoadingViewHolder -> {
            }

        }
    }

    fun setData(data: List<Restaurent>) {
        if (data.count() == restaurentList.count())
            hideLoadMore()
        else {
            if (restaurentList.isEmpty()) {
                restaurentList = data
                notifyItemRangeInserted(1, data.count())
            } else {
                val startIndex = restaurentList.count()
                restaurentList = data
                notifyDataSetChanged()
                notifyItemRangeInserted(startIndex, data.count());
            }
        }
    }

    fun setClickListener(clickListener: RestaurentClickListener?) {
        this.clickListener = clickListener
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val restaurentName: TextView
        val cuisnse: TextView
        val address: TextView
        val rating: TextView
        val thumb: ImageView

        init {
            restaurentName = view.findViewById(R.id.restaurent_name)
            cuisnse = view.findViewById(R.id.cuisine)
            address = view.findViewById(R.id.address)
            rating = view.findViewById(R.id.rating)
            thumb = view.findViewById(R.id.restaurent_thumb)
        }

        fun bind(restaurent: Restaurent, clickListener: RestaurentClickListener?) {
            restaurentName.text = restaurent.name
            rating.text = restaurent.rating
            rating.setBackgroundColor(Color.parseColor("#" + restaurent.ratingColor))
            address.text = restaurent.address
            cuisnse.text = restaurent.cuisine
            Glide.with(thumb.context)
                .load(restaurent.thumbUrl)
                .placeholder(R.drawable.restaurent_placeholder)
                .apply(RequestOptions().transform(RoundedCorners(10)))
                .into(thumb)

            itemView.setOnClickListener { clickListener?.onRestaurentClicked(restaurent.restaurentId) }
        }
    }
}