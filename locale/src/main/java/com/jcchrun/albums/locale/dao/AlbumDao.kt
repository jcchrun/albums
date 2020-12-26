package com.jcchrun.albums.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jcchrun.albums.locale.entities.AlbumEntity
import com.jcchrun.albums.locale.entities.AlbumEntityType
import com.jcchrun.albums.locale.entities.AlbumsColumnNames
import com.jcchrun.albums.locale.entities.AlbumsTable

@Dao
abstract class AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAlbums(albums: List<AlbumEntity>): Array<Long>

    @Query(
        "UPDATE ${AlbumsTable.TABLE_NAME} SET type =:albumEntityType WHERE ${AlbumsColumnNames.ID} IN (:idList)"
    )
    abstract suspend fun updateAlbumEntityType(idList: Set<Int>, albumEntityType: AlbumEntityType)

    @Query("DELETE FROM ${AlbumsTable.TABLE_NAME}")
    abstract suspend fun deleteAll()

    @Query(
        "SELECT * from ${AlbumsTable.TABLE_NAME}  WHERE ${AlbumsColumnNames.TYPE} =:albumEntityType"
    )
    abstract suspend fun getByAlbumType(albumEntityType: AlbumEntityType): List<AlbumEntity>

    @Query(
        "SELECT * from ${AlbumsTable.TABLE_NAME}  WHERE ${AlbumsColumnNames.ALBUM_ID} =:albumId"
    )
    abstract suspend fun getByAlbumId(albumId: Int): List<AlbumEntity>

    @Query(
        "SELECT COUNT(${AlbumsColumnNames.ID}) FROM ${AlbumsTable.TABLE_NAME}"
    )
    abstract suspend fun getCount(): Int
}