package com.jcchrun.album.core.extensions

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

val Context.isConnected: Boolean
    get() {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return when {
            cm == null -> {
                false
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                checkConnection(cm)
            }
            else -> {
                checkConnectionLegacy(cm)
            }
        }
    }

@TargetApi(Build.VERSION_CODES.M)
private fun checkConnection(cm: ConnectivityManager): Boolean {
    val networkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork)
    return when {
        networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> true
        networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> true
        networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> true
        else -> false
    }
}

@Suppress("DEPRECATION")
private fun checkConnectionLegacy(cm: ConnectivityManager): Boolean {
    return when (cm.activeNetworkInfo?.type) {
        ConnectivityManager.TYPE_WIFI -> true
        ConnectivityManager.TYPE_MOBILE -> true
        else -> false
    }
}

val Context.isPortrait: Boolean
    get() {
        return resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT
    }