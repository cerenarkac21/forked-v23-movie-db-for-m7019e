package com.ltu.m7019e.v23.themoviedb.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.ltu.m7019e.v23.themoviedb.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetworkStatusCallback(private val context: Context, val repository: MoviesRepository) :
    ConnectivityManager.NetworkCallback() {
    // Register the network callback
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Check network status
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    override fun onAvailable(network: Network) {
        // Network is available
        super.onAvailable(network)
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true){
            CoroutineScope(Dispatchers.IO).launch {
                repository.getPopularMovies()
            }
            CoroutineScope(Dispatchers.IO).launch {
                repository.getTopRatedMovies()
            }
        }

    }

    override fun onLost(network: Network) {
        // Network is lost
        super.onLost(network)

    }

    // Register the network callback when needed
    fun registerNetworkCallback() {
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    // Unregister the network callback when no longer needed
    fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(this)
    }

}
