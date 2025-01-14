package com.ltu.m7019e.v23.themoviedb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.model.PopularMovie
import com.ltu.m7019e.v23.themoviedb.model.SavedMovie
import com.ltu.m7019e.v23.themoviedb.model.TopRatedMovie

@Database(entities = [Movie::class, TopRatedMovie::class, PopularMovie::class, SavedMovie::class], version = 5, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDatabaseDao: MovieDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                var instance = INSTANCE

                // if the instance (row of data) is new, create it
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "saved_movie_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                // if the instance (row of data) aldready exists, return it
                return instance
            }
        }


    }
}