package com.wengelef.kleanmvp.data

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserEntity(
        @SerializedName("login") @ColumnInfo(name = "name") var name: String = "",
        @SerializedName("avatar_url") @ColumnInfo(name = "avatar_url") var avatarUrl: String = "",
        @SerializedName("html_url") @ColumnInfo(name = "html_url") var htmlUrl: String = "") {
    @PrimaryKey(autoGenerate = true) var id: Long = 0L
}
