package com.wengelef.kleanmvp.search.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import com.wengelef.kleanmvp.MainApplication
import com.wengelef.kleanmvp.R
import com.wengelef.kleanmvp.data.UserEntity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fr_search.*
import timber.log.Timber
import javax.inject.Inject

class SearchFragment : Fragment(), SearchView {

    @Inject lateinit var presenter: SearchPresenter

    private val usersAdapter = UsersAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fr_search, container, false)
    }

    override fun onStart() {
        super.onStart()
        (activity.application as MainApplication).appComponent.inject(this)

        users_recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        users_recycler.adapter = usersAdapter

        presenter.start(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.destroy()
    }

    override fun searchInputChanges(): Observable<String> = RxTextView.textChanges(editText)
            .map { it.toString() }

    override fun showUsers(users: List<UserEntity>) {
        usersAdapter.users = users
        usersAdapter.notifyDataSetChanged()
    }

    override fun showError(error: String) {
        Timber.e("Error : $error")
    }
}

