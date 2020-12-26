package com.jcchrun.albums.locale.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = AlbumsTable.TABLE_NAME)
data class AlbumEntity(
    @PrimaryKey @ColumnInfo(name = AlbumsColumnNames.ID)
    val id: Int,
    @ColumnInfo(name = AlbumsColumnNames.ALBUM_ID)
    val albumId: Int,
    @ColumnInfo(name = AlbumsColumnNames.TITLE)
    val title: String,
    @ColumnInfo(name = AlbumsColumnNames.URL)
    val url: String,
    @ColumnInfo(name = AlbumsColumnNames.THUMBNAIL_URL)
    val thumbnailUrl: String,
    @ColumnInfo(name = AlbumsColumnNames.TYPE)
    val type: AlbumEntityType
)

enum class AlbumEntityType {
    ALBUM_ROOT, ALBUM_DESCENDANT
}

object AlbumsTable {
    const val TABLE_NAME = "albums_table"
}

object AlbumsColumnNames {
    const val ID = "id"
    const val ALBUM_ID = "album_id"
    const val TITLE = "title"
    const val URL = "url"
    const val THUMBNAIL_URL = "thumbnail_url"
    const val TYPE = "type"
}