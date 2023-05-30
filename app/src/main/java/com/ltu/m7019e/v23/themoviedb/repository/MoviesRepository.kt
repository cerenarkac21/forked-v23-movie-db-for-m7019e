package com.ltu.m7019e.v23.themoviedb.repository
import androidx.lifecycle.MutableLiveData
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.model.*
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.TMDBApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

interface  MoviesRepository {
    suspend fun getPopularMovies(dataFetchStatus: MutableLiveData<DataFetchStatus>): List<Movie>
    suspend fun getTopRatedMovies(dataFetchStatus: MutableLiveData<DataFetchStatus>): List<Movie>
    suspend fun getMovieReviews(movieId: Long): List<Review>
    suspend fun getMovieVideos(movieId: Long): List<Video>
    suspend fun getSavedMovies(): List<Movie>
}

class MoviesRepositoryImpl (

    private val apiService: TMDBApiService,
    private val movieDatabaseDao: MovieDatabaseDao

    ): MoviesRepository {
    override suspend fun getTopRatedMovies(dataFetchStatus: MutableLiveData<DataFetchStatus>): List<Movie> {
        var topRatedMovies: List<Movie>
        try {
            Timber.tag("TOP_RATED_MOVIES_FROM_REPO").d("you have NETWORK CONNECTION")
            // Fetch data from the network
            topRatedMovies = apiService.getTopRatedMovies().results
            dataFetchStatus.postValue(DataFetchStatus.DONE)
            // Update the local cache with the fetched data
            movieDatabaseDao.clearTopRatedMovies()
            topRatedMovies.forEach {movie ->
                movieDatabaseDao.insertTopRatedMovie(TopRatedMovie(movieId = movie.id))
                movieDatabaseDao.insert(movie)
            }
        } catch (exception: Exception){
            Timber.tag("TOP_RATED_MOVIES_FROM_REPO").d("NO NETWORK CONNECTION, USE LOCAL DATA")
            withContext(Dispatchers.IO){
                movieDatabaseDao.clearPopularMovies()
                dataFetchStatus.postValue(DataFetchStatus.ERROR)
                topRatedMovies = movieDatabaseDao.getTopRatedMovies()
            }

        }
        return topRatedMovies
    }

    override suspend fun getPopularMovies(dataFetchStatus: MutableLiveData<DataFetchStatus>): List<Movie> {
        var popularMovies: List<Movie>
        try {
            // Fetch data from the network
            popularMovies = apiService.getPopularMovies().results
            dataFetchStatus.postValue(DataFetchStatus.DONE)
            // Update the local cache with the fetched data
            movieDatabaseDao.clearPopularMovies()
            popularMovies.forEach {movie ->
                movieDatabaseDao.insertPopularMovie(PopularMovie(id=movie.id))
                movieDatabaseDao.insert(movie)
            }
        } catch (exception: Exception){
            Timber.tag("POPULAR_MOVIES_FROM_REPO").d("NO NETWORK CONNECTION, USE LOCAL DATA")
            withContext(Dispatchers.IO) {
                movieDatabaseDao.clearTopRatedMovies()
                dataFetchStatus.postValue(DataFetchStatus.ERROR)
                popularMovies = movieDatabaseDao.getPopularMovies()
            }
        }
        return popularMovies
    }

    override suspend fun getMovieReviews(movieId: Long): List<Review> {
        return apiService.getMovieReviews(movieId).results
    }

    override suspend fun getMovieVideos(movieId: Long): List<Video> {
        return apiService.getMovieVideos(movieId).results
    }

    override suspend fun getSavedMovies(): List<Movie> {
        return movieDatabaseDao.getSavedMovies()
    }
}