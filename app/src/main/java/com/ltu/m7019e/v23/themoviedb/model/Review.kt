package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "reviews")
data class Review(
    //@PrimaryKey()
    @Json(name = "id")
    var id: String,

    //@ColumnInfo(name = "author")
    @Json(name = "author")
    var author: String,

    //@ColumnInfo(name = "author_details")
    @Json(name = "author_details")
    var authorDetails: Author,

    //@ColumnInfo(name = "content")
    @Json(name = "content")
    var content: String,

    //@ColumnInfo(name = "created_at")
    @Json(name = "created_at")
    var createdAt: String,

    //@ColumnInfo(name = "updated_at")
    @Json(name = "updated_at")
    var updatedAt: String,

    //@ColumnInfo(name = "url")
    @Json(name = "url")
    var url: String
    ): Parcelable
