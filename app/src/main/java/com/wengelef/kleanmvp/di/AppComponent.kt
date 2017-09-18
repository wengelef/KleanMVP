package com.wengelef.kleanmvp.di

import com.wengelef.kleanmvp.search.view.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = arrayOf(AppModule::class, RestModule::class, DomainModule::class, DataModule::class))
interface AppComponent {

    fun inject(fragment: SearchFragment)

}

