package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltu.m7019e.v23.themoviedb.repository.MoviesRepository
import java.lang.IllegalArgumentException

class MovieListViewModelFactory(private val moviesRepository: MoviesRepository, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(moviesRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}