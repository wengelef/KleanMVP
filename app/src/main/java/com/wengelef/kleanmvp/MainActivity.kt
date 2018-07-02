/*
 * Copyright (c) wengelef 2017
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wengelef.kleanmvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.wengelef.kleanmvp.di.*
import com.wengelef.kleanmvp.login.LoginFragment
import com.wengelef.kleanmvp.signup.SignupFragment
import kotlinx.android.synthetic.main.ac_main.*

class MainActivity : AppCompatActivity() {

    val appComponent: AppComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .dataModule(DataModule())
            .domainModule(DomainModule())
            .restModule(RestModule())
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        sign_up_pager.adapter = SignUpPagerAdapter(supportFragmentManager)

        sign_up_pager.setOnTouchListener { view, motionEvent ->
            sign_up_pager.currentItem = sign_up_pager.currentItem
            true
        }

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_login -> sign_up_pager.currentItem = 0
                R.id.action_signup -> sign_up_pager.currentItem = 1
            }
            true
        }
    }

    class SignUpPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        private val pageTitles = arrayOf("Login", "Signup")

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> LoginFragment()
                1 -> SignupFragment()
                else -> throw IllegalStateException("Unsupported Fragment at index : $position")
            }
        }

        override fun getCount(): Int = pageTitles.size

        override fun getPageTitle(position: Int): CharSequence? = pageTitles[position]
    }
}