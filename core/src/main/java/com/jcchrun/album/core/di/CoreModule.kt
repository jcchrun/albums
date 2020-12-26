package com.jcchrun.album.core.di

import android.content.Context
import com.jcchrun.album.core.tools.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideNetworkHandler(@ApplicationContext context: Context) = NetworkHandler(context)
}