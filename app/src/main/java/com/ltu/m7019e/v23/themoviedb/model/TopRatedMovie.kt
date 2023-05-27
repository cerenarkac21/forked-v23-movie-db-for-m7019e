package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "top_rated_movies")
data class TopRatedMovie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "top_rated_movie_id")
    var topRatedMovieId: Long = 0L,

    @ColumnInfo(name = "id")
    var id: Long = 0L
) : Parcelable

