package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.MovieReviewResponse
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch

class MovieReviewListViewModel(
    movieId: Long,
    application: Application
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

    private fun getMovieReviews(){
        viewModelScope.launch {
            try {
                val movieReviewResponse: MovieReviewResponse = TMDBApi.movieReviewListRetrofitService.getMovieReviews(movieId)
                _movieReviewList.value = movieReviewResponse.results
                _dataFetchStatus.value = DataFetchStatus.DONE
            }
            catch (e: Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _movieReviewList.value = arrayListOf()
            }
        }
    }

    fun onMovieReviewListItemClicked(review: Review) {

    }

}