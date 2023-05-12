package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "videos")
data class Video(
    //@ColumnInfo(name = "iso_639_1")
    @Json(name = "iso_639_1")
    var iso6391: String,

    //@ColumnInfo(name = "iso_3166_1")
    @Json(name = "iso_3166_1")
    var iso31661: String,

    //@ColumnInfo(name = "name")
    @Json(name = "name")
    var name: String,

    //@ColumnInfo(name = "key")
    @Json(name = "key")
    var key: String,

    //@ColumnInfo(name = "site")
    @Json(name = "site")
    var site: String,

    //@ColumnInfo(name = "size")
    @Json(name = "size")
    var size: Int,

    //@ColumnInfo(name = "type")
    @Json(name = "type")
    var type: String,

    //@ColumnInfo(name = "official")
    @Json(name = "official")
    var official: Boolean,

    //@ColumnInfo(name = "published_at")
    @Json(name = "published_at")
    var publishedAt: String,

    //@PrimaryKey()
    @Json(name = "id")
    var id: String

): Parcelable

