package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "popular_movies")
data class PopularMovie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "popular_movie_id")
    var popularMovieId: Long = 0L,

    @ColumnInfo(name = "id")
    var id: Long = 0L
) : Parcelable