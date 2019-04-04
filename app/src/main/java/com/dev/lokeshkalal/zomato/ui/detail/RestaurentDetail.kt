package com.dev.lokeshkalal.zomato.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dev.lokeshkalal.zomato.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class RestaurentDetail : AppCompatActivity() {
    companion object {

        const val ARG_RESTATURENT_ID = "arg_restaurent_id"

        fun getRestaurentDetailIntent(context: Context, restaurentId: Int): Intent {
            val intent = Intent(context, RestaurentDetail::class.java)
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
