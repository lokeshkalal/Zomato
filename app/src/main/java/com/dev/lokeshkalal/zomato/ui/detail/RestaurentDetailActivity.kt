package com.dev.lokeshkalal.zomato.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.lokeshkalal.zomato.R

class RestaurentDetailActivity : AppCompatActivity() {
    companion object {

        const val ARG_RESTATURENT_ID = "arg_restaurent_id"

        fun getRestaurentDetailIntent(context: Context, restaurentId: Int): Intent {
            val intent = Intent(context, RestaurentDetailActivity::class.java)
            intent.putExtra(ARG_RESTATURENT_ID, restaurentId)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurent_detail_activity)
        if (savedInstanceState == null) {

            val restaurentId = intent.getIntExtra(ARG_RESTATURENT_ID, 0)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RestaurentDetailFragment.newInstance(restaurentId))
                .commitNow()
        }
    }

}
