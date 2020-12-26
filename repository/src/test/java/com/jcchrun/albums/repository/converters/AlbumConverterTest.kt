package com.jcchrun.albums.repository.converters

import com.jcchrun.albums.locale.entities.AlbumEntity
import com.jcchrun.albums.locale.entities.AlbumEntityType
import com.jcchrun.albums.remote.dto.AlbumDto
import org.junit.Assert
import org.junit.Test

class AlbumConverterTest {

    private val albumConverter = AlbumConverter()

    @Test
    fun `test convertDtoToEntity`() {
        val dto = AlbumDto(
            2,
            2,
            "album title",
            "album url",
            "album thumbnail"
        )

        val entity = albumConverter.convertDtoToEntity(dto, AlbumEntityType.ALBUM_ROOT)

        Assert.assertEquals(2, entity.id)
        Assert.assertEquals(2, entity.albumId)
        Assert.assertEquals("album title", entity.title)
        Assert.assertEquals("album url", entity.url)
        Assert.assertEquals("album thumbnail", entity.thumbnailUrl)
        Assert.assertEquals(AlbumEntityType.ALBUM_ROOT, entity.type)
    }

    @Test
    fun `test convertEntityToModel`() {
        val entity = AlbumEntity(
            5,
            2,
            "album title",
            "album url",
            "album thumbnail",
            AlbumEntityType.ALBUM_DESCENDANT
        )

        val model = albumConverter.convertEntityToModel(entity)

        Assert.assertEquals(5, model.id)
        Assert.assertEquals(2, model.albumId)
        Assert.assertEquals("album title", model.title)
        Assert.assertEquals("album url", model.url)
        Assert.assertEquals("album thumbnail", model.thumbnailUrl)
    }
}