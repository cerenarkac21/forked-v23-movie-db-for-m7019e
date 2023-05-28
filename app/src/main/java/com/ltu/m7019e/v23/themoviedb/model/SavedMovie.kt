package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "saved_movies")
data class SavedMovie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saved_movie_id")
    var savedMovieId: Long = 0L,

    @ColumnInfo(name = "id")
    var movieId: Long = 0L
) : Parcelable

