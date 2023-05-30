package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.repository.MoviesRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieListViewModel(private val moviesRepository: MoviesRepository, application: Application) : AndroidViewModel(application) {

    //private val networkStatusCallback = NetworkStatusCallback(application, moviesRepository)

    enum class MoviesType {
        POPULAR, TOP_RATED, SAVED
    }

    var lastFetchedMoviesType: MoviesType = MoviesType.POPULAR

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() {
            return _movieList
        }

    private val _navigateToMovieDetail = MutableLiveData<Movie?>()
    val navigateToMovieDetail: MutableLiveData<Movie?>
        get() {
            return _navigateToMovieDetail
        }

    init {
        getPopularMovies()
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    fun onMovieListItemClicked(movie: Movie) {
        _navigateToMovieDetail.value = movie
    }

    fun onMovieDetailNavigated() {
        _navigateToMovieDetail.value = null
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            lastFetchedMoviesType = MoviesType.POPULAR
            try {
                /*
                don't need anymore. use the repository pattern
                val movieResponse: MovieResponse =
                    TMDBApi.movieListRetrofitService.getPopularMovies()

                 */
                _movieList.value = moviesRepository.getPopularMovies(_dataFetchStatus)

            } catch (e: Exception) {
                _movieList.value = arrayListOf()
            }
            Timber.tag("getPopularMovies in view model after try and catch:").d(_dataFetchStatus.value.toString())

        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            lastFetchedMoviesType = MoviesType.TOP_RATED
            try {
                _movieList.value = moviesRepository.getTopRatedMovies(_dataFetchStatus)

            } catch (e: Exception) {
                _movieList.value = arrayListOf()
            }
            Timber.tag("getTopRatedMovies in view model after try and catch:").d(_dataFetchStatus.value.toString())

        }
    }

    fun getSavedMovies() {
        viewModelScope.launch {
            val movies = moviesRepository.getSavedMovies()
            _movieList.value = movies
            lastFetchedMoviesType = MoviesType.SAVED
        }
    }
}