package com.jcchrun.albums.locale.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jcchrun.albums.locale.dao.AlbumDao
import com.jcchrun.albums.locale.entities.AlbumEntity

@Database(entities = [AlbumEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AlbumDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    companion object {
        val DATABASE_NAME = "album_table"
    }
}