package com.wengelef.kleanmvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wengelef.kleanmvp.search.view.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SearchFragment(), "SearchFragment")
                .commit()
    }
}