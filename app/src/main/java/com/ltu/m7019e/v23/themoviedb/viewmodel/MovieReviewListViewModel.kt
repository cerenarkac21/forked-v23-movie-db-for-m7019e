package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.network.AppContainerImpl
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.repository.MoviesRepository
import kotlinx.coroutines.launch

class MovieReviewListViewModel(
    movieId: Long,
    application: Application, private val moviesRepository: MoviesRepository
) : AndroidViewModel(application) {

    private val movieId = movieId

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _movieReviewList = MutableLiveData<List<Review>>()
    val movieReviewList: LiveData<List<Review>>
        get() {
            return _movieReviewList
        }

    init {
        getMovieReviews()
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    private fun getMovieReviews() {
        viewModelScope.launch {
            try {
                _movieReviewList.value = moviesRepository.getMovieReviews(movieId)
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _movieReviewList.value = arrayListOf()
            }
        }
    }

    fun onMovieReviewListItemClicked(review: Review) {

    }

}