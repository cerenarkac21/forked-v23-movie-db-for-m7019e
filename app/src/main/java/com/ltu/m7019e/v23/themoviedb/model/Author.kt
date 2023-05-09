package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Author(
    @PrimaryKey()
    @Json(name = "username")
    var username: String,

    @ColumnInfo(name = "name")
    @Json(name = "name")
    var name: String,

    @ColumnInfo(name = "avatar_path")
    @Json(name = "avatar_path")
    var avatarPath: String,

    @ColumnInfo(name = "rating")
    @Json(name = "rating")
    var rating: Double?
    ): Parcelable
