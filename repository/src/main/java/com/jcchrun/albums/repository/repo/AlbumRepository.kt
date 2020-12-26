package com.jcchrun.albums.repository.repo

import com.jcchrun.album.core.tools.NetworkHandler
import com.jcchrun.albums.locale.dao.AlbumDao
import com.jcchrun.albums.locale.entities.AlbumEntityType
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.models.Output
import com.jcchrun.albums.remote.WebService
import com.jcchrun.albums.repository.converters.AlbumConverter
import javax.inject.Inject

interface AlbumRepository {

    suspend fun getAlbumRootList(): Output<List<Album>>

    suspend fun getAlbumList(albumId: Int): List<Album>
}

class AlbumRepositoryImpl @Inject constructor(
    private val webService: WebService,
    private val albumDao: AlbumDao,
    private val converter: AlbumConverter,
    private val networkHandler: NetworkHandler
): AlbumRepository {

    override suspend fun getAlbumRootList(): Output<List<Album>> {
        val errorOutput = try {
            val albumDtosList = webService.fetchAlbums()
            val albumEntitiesList = albumDtosList.map {
                converter.convertDtoToEntity(it, AlbumEntityType.ALBUM_DESCENDANT)
            }.filter {
                // Ignores instances without id attribute (it's default value is -1)
                it.id > 0
            }
            if (albumEntitiesList.isNotEmpty()) {
                albumDao.deleteAll()
                albumDao.insertAlbums(albumEntitiesList)
                albumDao.updateAlbumEntityType(albumEntitiesList.map { it.albumId }.toSet(), AlbumEntityType.ALBUM_ROOT)
            }
            null
        }
        catch (e: Exception) {
            if (!networkHandler.isConnected) {
                Output.Error(Output.Error.ERROR_CODE_NO_NETWORK, e.message ?: "", e)
            }
            else Output.Error(Output.Error.ERROR_CODE_UNKNOWN, e.message ?: "", e)
        }

        val cachedAlbumList = albumDao.getByAlbumType(AlbumEntityType.ALBUM_ROOT)

        return when {
            cachedAlbumList.isNotEmpty() -> {
                Output.Success(cachedAlbumList.map {
                    converter.convertEntityToModel(it)
                })
            }
            errorOutput != null -> {
                errorOutput
            }
            else -> {
                Output.Success(listOf())
            }
        }
    }

    override suspend fun getAlbumList(albumId: Int): List<Album> {
        return albumDao.getByAlbumId(albumId).map {
            converter.convertEntityToModel(it)
        }
    }
}