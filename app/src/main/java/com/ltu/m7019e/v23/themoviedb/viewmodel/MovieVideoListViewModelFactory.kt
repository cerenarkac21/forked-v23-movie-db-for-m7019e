package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltu.m7019e.v23.themoviedb.repository.MoviesRepository
import java.lang.IllegalArgumentException

class MovieVideoListViewModelFactory (private val movie_id: Long, private val application: Application, private val moviesRepository: MoviesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieVideoListViewModel::class.java)) {
            return MovieVideoListViewModel(movie_id, application, moviesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

