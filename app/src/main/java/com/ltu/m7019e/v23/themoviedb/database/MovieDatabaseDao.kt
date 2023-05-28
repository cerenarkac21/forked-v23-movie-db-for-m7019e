package com.ltu.m7019e.v23.themoviedb.database

import androidx.room.*
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.model.PopularMovie
import com.ltu.m7019e.v23.themoviedb.model.SavedMovie
import com.ltu.m7019e.v23.themoviedb.model.TopRatedMovie

@Dao
interface MovieDatabaseDao {
    @Insert
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRatedMovie(topRatedMovie: TopRatedMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovie(popularMovie: PopularMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedMovie(savedMovie: SavedMovie)

    @Delete
    suspend fun deleteSavedMovie(savedMovie: SavedMovie)

    @Query("DELETE FROM top_rated_movies")
    suspend fun clearTopRatedMovies()

    @Query("DELETE FROM popular_movies")
    suspend fun clearPopularMovies()

    @Query("SELECT * FROM movies INNER JOIN top_rated_movies ON movies.id = top_rated_movies.id")
    suspend fun getTopRatedMovies(): List<Movie>

    @Query("SELECT * FROM movies INNER JOIN popular_movies ON movies.id = popular_movies.id")
    suspend fun getPopularMovies(): List<Movie>

    @Query("SELECT * FROM movies INNER JOIN saved_movies ON movies.id = saved_movies.id")
    suspend fun getSavedMovies(): List<Movie>

    @Query("SELECT * from movies ORDER BY id ASC")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT EXISTS(SELEcT * from movies WHERE id = :id)")
    suspend fun isFavorite(id: Long): Boolean

}