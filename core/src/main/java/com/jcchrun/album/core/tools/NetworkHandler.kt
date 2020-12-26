package com.jcchrun.album.core.tools

import android.content.Context
import com.jcchrun.album.core.extensions.isConnected
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {
    val isConnected get() = context.isConnected
}