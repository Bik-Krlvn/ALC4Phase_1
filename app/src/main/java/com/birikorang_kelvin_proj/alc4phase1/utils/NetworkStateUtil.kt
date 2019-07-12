package com.birikorang_kelvin_proj.alc4phase1.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkStateUtil {
    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}