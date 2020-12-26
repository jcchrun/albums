package com.jcchrun.albums.locale.db

import androidx.room.TypeConverter
import com.jcchrun.albums.locale.entities.AlbumEntityType

class Converters {

    @TypeConverter
    fun stringToAlbumDtoType(value: String): AlbumEntityType {
        return when (value) {
            AlbumEntityType.ALBUM_ROOT.name -> AlbumEntityType.ALBUM_ROOT
            else -> AlbumEntityType.ALBUM_DESCENDANT
        }
    }

    @TypeConverter
    fun albumDtoTypeToString(albumEntityType: AlbumEntityType): String {
        return albumEntityType.name
    }
}