package com.wengelef.kleanmvp.di

import android.arch.persistence.room.Room
import android.content.Context
import com.google.gson.Gson
import com.wengelef.kleanmvp.data.*
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideUserRepository(@Named("Room") userDb: UserDb, userService: UserService): UserRepository {
        return UserRepositoryImpl(userDb, userService)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }

    @Provides
    @Singleton
    @Named("Prefs")
    fun provideUserPrefs(context: Context, gson: Gson): UserDb {
        return UserPrefs(context, gson)
    }

    @Provides
    @Singleton
    @Named("Room")
    fun provideUserRoom(appDatabase: AppDatabase): UserDb {
        return UserRoom(appDatabase.userDao())
    }
}