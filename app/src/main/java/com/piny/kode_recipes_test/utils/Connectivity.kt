package com.piny.kode_recipes_test.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class Connectivity(private val context: Context) {
    @Suppress("DEPRECATION")
    fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }
}