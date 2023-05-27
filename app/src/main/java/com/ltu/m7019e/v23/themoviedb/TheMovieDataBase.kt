package com.ltu.m7019e.v23.themoviedb

import android.app.Application
import android.content.Context
import com.ltu.m7019e.v23.themoviedb.network.AppContainer
import com.ltu.m7019e.v23.themoviedb.network.AppContainerImpl
import timber.log.Timber

// call this file in the manifest file
class TheMovieDataBase : Application() {

    lateinit var container: AppContainer

    companion object {
        fun getContainer(context: Context): AppContainer {
            return (context.applicationContext as TheMovieDataBase).container
        }
    }

    override fun onCreate() {
        super.onCreate()

        // sets up the background tasks, i.e.,
        // it will make the expensive operations run in a background thread
        // to prevent delays to start the app.

        container = AppContainerImpl(this)
        Timber.plant(Timber.DebugTree())

    }
}