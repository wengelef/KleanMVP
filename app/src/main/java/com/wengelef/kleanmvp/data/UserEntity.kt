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
 * distributed under the License isdistributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wengelef.kleanmvp.data

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserEntity(
        @SerializedName("login") @ColumnInfo(name = "name") var name: String = "",
        @SerializedName("avatar_url") @ColumnInfo(name = "avatar_url") var avatarUrl: String = "",
        @SerializedName("html_url") @ColumnInfo(name = "html_url") var htmlUrl: String = "",
        @ColumnInfo(name = "following") var isFollowing: Boolean = false) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0L
}
