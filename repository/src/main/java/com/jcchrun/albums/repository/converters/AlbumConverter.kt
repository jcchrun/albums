package com.jcchrun.albums.repository.converters

import com.jcchrun.albums.locale.entities.AlbumEntityType
import com.jcchrun.albums.locale.entities.AlbumEntity
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.remote.dto.AlbumDto
import javax.inject.Inject

class AlbumConverter @Inject constructor() {

    fun convertDtoToEntity(albumDto: AlbumDto, defaultAlbumEntityType: AlbumEntityType): AlbumEntity {
        return AlbumEntity(
            albumDto.id,
            albumDto.albumId,
            albumDto.title ?: "",
            albumDto.url ?: "",
            albumDto.thumbnailUrl ?: "",
            defaultAlbumEntityType
        )
    }

    fun convertEntityToModel(albumEntity: AlbumEntity): Album {
        return Album(
            albumEntity.id,
            albumEntity.albumId,
            albumEntity.title,
            albumEntity.url,
            albumEntity.thumbnailUrl
        )
    }
}