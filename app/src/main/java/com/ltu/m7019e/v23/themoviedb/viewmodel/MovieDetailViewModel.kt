package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.model.SavedMovie
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieDatabaseDao: MovieDatabaseDao,
    application: Application,
    movie: Movie
) : AndroidViewModel(application){

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() {
            return _isFavorite
        }


    init {
        setIsFavorite(movie)
    }


    private fun setIsFavorite(movie: Movie) {
        viewModelScope.launch {
            _isFavorite.value = movieDatabaseDao.isFavorite(movie.id)
        }
    }

    fun onSaveMovieButtonClicked(movie: Movie) {
        viewModelScope.launch {
            movieDatabaseDao.insertSavedMovie(SavedMovie(movieId = movie.id))
            setIsFavorite(movie)
        }
    }

    fun onRemoveMovieButtonClicked(movie: Movie) {
        viewModelScope.launch {
            movieDatabaseDao.deleteSavedMovie(movie.id)
            setIsFavorite(movie)
        }
    }

}