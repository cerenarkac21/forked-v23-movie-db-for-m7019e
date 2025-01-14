package com.ltu.m7019e.v23.themoviedb.network

import android.content.Context
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabase
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.repository.MoviesRepository
import com.ltu.m7019e.v23.themoviedb.repository.MoviesRepositoryImpl
import com.ltu.m7019e.v23.themoviedb.utils.CONSTANTS
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface AppContainer {
    val moviesRepository : MoviesRepository
    val movieDatabaseDao : MovieDatabaseDao
}
class AppContainerImpl (context: Context): AppContainer {

    private val movieDatabase = MovieDatabase.getInstance(context)

    override val movieDatabaseDao: MovieDatabaseDao
        get() = movieDatabase.movieDatabaseDao
    /**
     * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
     * full Kotlin compatibility.
     * (JSON to Kotlin mapper)
     */
    private val moshi = Moshi.Builder()
        // use the built-in adapter factory to map the JSON into your classes
        .add(KotlinJsonAdapterFactory())
        .build()


    /**
     * Add a httpclient logger for debugging purpose
     * object.
     * Also it enables you to define your own encryption/decryption logic
     */
    fun getLoggerInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    /**
     * Refrofit is a HTTP client that will query the API endpoints
     * Moshi will parse JSON objects to Kotlin objects
     * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
     * object.
     */

    private val retrofit = Retrofit.Builder()
        // create a client
        .client(
            // to use the intercepter defined above
            OkHttpClient.Builder()
                .addInterceptor(getLoggerInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(CONSTANTS.MOVIE_LIST_BASE_URL)
        .build()


    private val retrofitService: TMDBApiService by lazy {
        retrofit.create(TMDBApiService::class.java)
    }

    override val moviesRepository : MoviesRepository by lazy {
        MoviesRepositoryImpl(retrofitService, movieDatabaseDao)
    }

}

// create the main interface that defines TMDBApiService
interface TMDBApiService {
    // the string that will be added to the base URL is "popular"
    @GET("popular")
    suspend fun getPopularMovies(
        // after /popular?, "api_key" will be added to the URL
        @Query("api_key")
        apiKey: String = CONSTANTS.API_KEY
    ): MovieResponse

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = CONSTANTS.API_KEY
    ): MovieResponse

    @GET("{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id")
        movieId: Long,
        @Query("api_key")
        apiKey: String = CONSTANTS.API_KEY
    ): MovieReviewResponse

    @GET("{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id")
        movieId: Long,
        @Query("api_key")
        apiKey: String = CONSTANTS.API_KEY
    ): MovieVideoResponse
}