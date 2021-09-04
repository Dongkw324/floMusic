package com.kdw.flomusic.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

@Suppress("DEPRECATION")
class NetworkCheck {
    fun isNetworkConnected(context: Context?): Boolean {
        var result = false

        if(context == null)
            return false

        val networkManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilities = networkManager.getNetworkCapabilities(networkManager.activeNetwork)
            if(networkCapabilities != null) {
                if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    result = true
                } else if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    result = true
                } else if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    result = true
                }
            }
        } else {
            val activeNetwork = networkManager.activeNetworkInfo
            if(activeNetwork != null && activeNetwork.isConnected){
                result = true
            }
        }

        return result
    }
}