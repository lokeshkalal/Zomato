package com.dev.lokeshkalal.zomato.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.lokeshkalal.zomato.R

class RestaurentDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurent_detail_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RestaurentDetailFragment.newInstance())
                .commitNow()
        }
    }

}
