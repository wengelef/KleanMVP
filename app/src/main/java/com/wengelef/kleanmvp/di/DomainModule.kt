package com.wengelef.kleanmvp.di

import com.wengelef.kleanmvp.data.UserRepository
import com.wengelef.kleanmvp.domain.UserInteractor
import com.wengelef.kleanmvp.domain.UserInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton @Module
class DomainModule {

    @Provides
    @Singleton
    fun provideUserInteractor(userRepository: UserRepository): UserInteractor {
        return UserInteractorImpl(userRepository)
    }
}