package com.dev.lokeshkalal.zomato.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.lokeshkalal.zomato.R

class HomeScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeScreenFragment.newInstance())
                .commitNow()
        }
    }

}
