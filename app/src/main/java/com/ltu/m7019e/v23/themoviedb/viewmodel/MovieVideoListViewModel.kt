package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.ltu.m7019e.v23.themoviedb.model.Video
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.MovieVideoResponse
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch

class MovieVideoListViewModel (
        movieId: Long,
        application: Application
    ) : AndroidViewModel(application) {

        private val movieId = movieId
        private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
        val dataFetchStatus: LiveData<DataFetchStatus>
            get() {
                return _dataFetchStatus
            }

        private val _movieVideoList = MutableLiveData<List<Video>>()
        val movieVideoList: LiveData<List<Video>>
            get() {
                return _movieVideoList
            }

        init {
            getMovieVideos()
            _dataFetchStatus.value = DataFetchStatus.LOADING
        }

        private fun getMovieVideos(){
            viewModelScope.launch {
                try {
                    val movieVideoResponse: MovieVideoResponse = TMDBApi.movieVideoListRetrofitService.getMovieVideos(movieId)
                    _movieVideoList.value = movieVideoResponse.results
                    _dataFetchStatus.value = DataFetchStatus.DONE
                }
                catch (e: Exception){
                    _dataFetchStatus.value = DataFetchStatus.ERROR
                    _movieVideoList.value = arrayListOf()
                }
            }
        }

        fun onMovieVideoListItemClicked(video: Video) {

        }

}

