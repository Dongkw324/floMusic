package com.kdw.flomusic.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController

@Suppress("DEPRECATION")
object OtherFunctions {
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

    fun makeStatusTransparent(activity: Activity) {
        val status = activity.window.decorView
        val w = activity.window
        w.statusBarColor = Color.BLACK

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            status.windowInsetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        } else {
            status.systemUiVisibility = View.STATUS_BAR_VISIBLE
        }
    }
}