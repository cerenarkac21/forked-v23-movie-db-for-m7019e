package com.ltu.m7019e.v23.themoviedb.repository
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.model.*
import com.ltu.m7019e.v23.themoviedb.network.TMDBApiService
import timber.log.Timber

interface  MoviesRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getMovieReviews(movieId: Long): List<Review>
    suspend fun getMovieVideos(movieId: Long): List<Video>
    suspend fun getSavedMovies(): List<Movie>
}

class MoviesRepositoryImpl (

    private val apiService: TMDBApiService,
    private val movieDatabaseDao: MovieDatabaseDao

    ): MoviesRepository {
    override suspend fun getTopRatedMovies(): List<Movie> {
        try {
            Timber.tag("TOP_RATED_MOVIES_FROM_REPO").d("you have NETWORK CONNECTION")
            // Fetch data from the network
            val topRatedMovies = apiService.getTopRatedMovies().results
            // Update the local cache with the fetched data
            movieDatabaseDao.clearTopRatedMovies()
            topRatedMovies.forEach {movie ->
                movieDatabaseDao.insertTopRatedMovie(TopRatedMovie(movieId = movie.id))
                movieDatabaseDao.insert(movie)
            }
            return topRatedMovies
        } catch (exception: Exception){
            Timber.tag("TOP_RATED_MOVIES_FROM_REPO").d("NO NETWORK CONNECTION, USE LOCAL DATA")

        }
        return movieDatabaseDao.getTopRatedMovies()
    }

    override suspend fun getPopularMovies(): List<Movie> {
        try {
            // Fetch data from the network
            val popularMovies = apiService.getPopularMovies().results
            // Update the local cache with the fetched data
            movieDatabaseDao.clearPopularMovies()
            popularMovies.forEach {movie ->
                movieDatabaseDao.insertPopularMovie(PopularMovie(id=movie.id))
                movieDatabaseDao.insert(movie)
            }
            return popularMovies
        } catch (exception: Exception){
            Timber.tag("POPULAR_MOVIES_FROM_REPO").d("NO NETWORK CONNECTION, USE LOCAL DATA")
        }
        return movieDatabaseDao.getPopularMovies()
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