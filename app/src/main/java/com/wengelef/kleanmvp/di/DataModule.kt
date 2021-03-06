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

package com.wengelef.kleanmvp.di

import dagger.Module
import javax.inject.Singleton

@Singleton
@Module
class DataModule {
/*
    @Provides
    @Singleton
    fun provideUserRepository(@Named("Room") userDb: UserDb, userService: UserService): UserRepository {
        return UserRepositoryImpl(userDb, userService)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                .fallbackToDestructiveMigration()
                .build()
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
    }*/
}