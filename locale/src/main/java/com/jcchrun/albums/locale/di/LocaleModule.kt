package com.jcchrun.albums.locale.di

import android.content.Context
import androidx.room.Room
import com.jcchrun.albums.locale.db.AlbumDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocaleModule {

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ): AlbumDatabase = Room.databaseBuilder(
        context,
        AlbumDatabase::class.java,
        AlbumDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideAlbumDao(db: AlbumDatabase) = db.albumDao()
}