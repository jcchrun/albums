package com.jcchrun.albums.repository

import android.accounts.NetworkErrorException
import com.jcchrun.album.core.tools.NetworkHandler
import com.jcchrun.albums.locale.dao.AlbumDao
import com.jcchrun.albums.locale.entities.AlbumEntity
import com.jcchrun.albums.locale.entities.AlbumEntityType
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.models.Output
import com.jcchrun.albums.remote.WebService
import com.jcchrun.albums.remote.dto.AlbumDto
import com.jcchrun.albums.repository.converters.AlbumConverter
import com.jcchrun.albums.repository.repo.AlbumRepository
import com.jcchrun.albums.repository.repo.AlbumRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

class AlbumRepositoryTest {

    @MockK
    lateinit var mockWebService: WebService

    @MockK
    lateinit var mockAlbumDao: AlbumDao

    @MockK
    lateinit var mockAlbumConverter: AlbumConverter

    @MockK
    lateinit var mockNetworkHandler: NetworkHandler

    @MockK
    lateinit var mockAlbumEntity: AlbumEntity

    @MockK
    lateinit var mockAlbumDto: AlbumDto

    @MockK
    lateinit var mockAlbum: Album

    private lateinit var albumRepository: AlbumRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        albumRepository = AlbumRepositoryImpl(
            mockWebService,
            mockAlbumDao,
            mockAlbumConverter,
            mockNetworkHandler
        )
    }

    @Test
    fun `test getAlbumRootList no network and no cached data`() {
        coEvery { mockWebService.fetchAlbums() } throws NetworkErrorException()
        coEvery { mockNetworkHandler.isConnected } returns false
        coEvery { mockAlbumDao.getByAlbumType(any()) } returns listOf()
        //
        val output = runBlocking { albumRepository.getAlbumRootList() }
        //
        Assert.assertTrue(output is Output.Error)
        Assert.assertEquals(Output.Error.ERROR_CODE_NO_NETWORK, (output as Output.Error).errorCode)
        coVerify { mockWebService.fetchAlbums() }
    }

    @Test
    fun `test getAlbumRootList no network and with cached data`() {
        coEvery { mockWebService.fetchAlbums() } throws NetworkErrorException()
        coEvery { mockNetworkHandler.isConnected } returns false
        coEvery { mockAlbumDao.getByAlbumType(any()) } returns listOf(mockAlbumEntity)
        coEvery { mockAlbumConverter.convertEntityToModel(any()) } returns mockAlbum
        //
        val output = runBlocking { albumRepository.getAlbumRootList() }
        //
        Assert.assertTrue(output is Output.Success)
        Assert.assertEquals(1, (output as Output.Success).result.size)
        coVerify { mockWebService.fetchAlbums() }
        coVerify { mockAlbumDao.getByAlbumType(AlbumEntityType.ALBUM_ROOT) }
        coVerify { mockAlbumConverter.convertEntityToModel(mockAlbumEntity) }
    }

    @Test
    fun `test getAlbumRootList fetch empty list`() {
        coEvery { mockWebService.fetchAlbums() } returns listOf()
        coEvery { mockAlbumDao.getByAlbumType(any()) } returns listOf()
        //
        val output = runBlocking { albumRepository.getAlbumRootList() }
        //
        Assert.assertTrue(output is Output.Success)
        Assert.assertEquals(0, (output as Output.Success).result.size)
        coVerify { mockWebService.fetchAlbums() }
        coVerify { mockAlbumDao.getByAlbumType(AlbumEntityType.ALBUM_ROOT) }
    }

    @Test
    fun `test getAlbumRootList fetch throws exception and no cached data`() {
        coEvery { mockWebService.fetchAlbums() } throws IllegalArgumentException()
        coEvery { mockNetworkHandler.isConnected } returns true
        coEvery { mockAlbumDao.getByAlbumType(any()) } returns listOf()
        //
        val output = runBlocking { albumRepository.getAlbumRootList() }
        //
        Assert.assertTrue(output is Output.Error)
        Assert.assertEquals(Output.Error.ERROR_CODE_UNKNOWN, (output as Output.Error).errorCode)
        Assert.assertTrue(output.exception is IllegalArgumentException)
        coVerify { mockWebService.fetchAlbums() }
        coVerify { mockAlbumDao.getByAlbumType(AlbumEntityType.ALBUM_ROOT) }
    }

    @Test
    fun `test getAlbumRootList fetch throws exception with cached data`() {
        coEvery { mockWebService.fetchAlbums() } throws IllegalArgumentException()
        coEvery { mockNetworkHandler.isConnected } returns true
        coEvery { mockAlbumDao.getByAlbumType(any()) } returns listOf(mockAlbumEntity)
        coEvery { mockAlbumConverter.convertEntityToModel(any()) } returns mockAlbum
        //
        val output = runBlocking { albumRepository.getAlbumRootList() }
        //
        Assert.assertTrue(output is Output.Success)
        Assert.assertEquals(1, (output as Output.Success).result.size)
        coVerify { mockWebService.fetchAlbums() }
        coVerify { mockAlbumDao.getByAlbumType(AlbumEntityType.ALBUM_ROOT) }
        coVerify { mockAlbumConverter.convertEntityToModel(mockAlbumEntity) }
    }

    @Test
    fun `test getAlbumRootList all good`() {
        coEvery { mockWebService.fetchAlbums() } returns listOf(mockAlbumDto)
        coEvery { mockAlbumDao.deleteAll() } answers { }
        coEvery { mockAlbumDao.updateAlbumEntityType(any(), any()) } answers { }
        coEvery { mockAlbumDao.insertAlbums(any()) } returns arrayOf(1L)
        coEvery { mockAlbumDao.getByAlbumType(any()) } returns listOf(mockAlbumEntity)
        coEvery { mockAlbumEntity.id } returns 1
        coEvery { mockAlbumEntity.albumId } returns 1
        coEvery { mockAlbumConverter.convertDtoToEntity(any(), any()) } returns mockAlbumEntity
        coEvery { mockAlbumConverter.convertEntityToModel(any()) } returns mockAlbum
        //
        val output = runBlocking { albumRepository.getAlbumRootList() }
        //
        Assert.assertTrue(output is Output.Success)
        Assert.assertEquals(1, (output as Output.Success).result.size)
        coVerify { mockWebService.fetchAlbums() }
        coVerify { mockAlbumDao.deleteAll() }
        coVerify { mockAlbumDao.updateAlbumEntityType(setOf(1), AlbumEntityType.ALBUM_ROOT) }
        coVerify { mockAlbumDao.insertAlbums(listOf(mockAlbumEntity)) }
        coVerify { mockAlbumDao.getByAlbumType(AlbumEntityType.ALBUM_ROOT) }
        coVerify { mockAlbumConverter.convertDtoToEntity(mockAlbumDto, AlbumEntityType.ALBUM_DESCENDANT) }
        coVerify { mockAlbumConverter.convertEntityToModel(mockAlbumEntity) }
    }

    @Test
    fun `test getAlbumRootList with negative id`() {
        coEvery { mockWebService.fetchAlbums() } returns listOf(mockAlbumDto)
        coEvery { mockAlbumDao.getByAlbumType(any()) } returns listOf()
        coEvery { mockAlbumEntity.id } returns -1
        coEvery { mockAlbumConverter.convertDtoToEntity(any(), any()) } returns mockAlbumEntity
        coEvery { mockAlbumConverter.convertEntityToModel(any()) } returns mockAlbum
        //
        val output = runBlocking { albumRepository.getAlbumRootList() }
        //
        Assert.assertTrue(output is Output.Success)
        Assert.assertEquals(0, (output as Output.Success).result.size)
        coVerify { mockWebService.fetchAlbums() }
        coVerify { mockAlbumDao.getByAlbumType(AlbumEntityType.ALBUM_ROOT) }
        coVerify { mockAlbumConverter.convertDtoToEntity(mockAlbumDto, AlbumEntityType.ALBUM_DESCENDANT) }
        coVerify(exactly = 0) { mockAlbumDao.deleteAll() }
        coVerify(exactly = 0) { mockAlbumDao.insertAlbums(any()) }
        coVerify(exactly = 0) { mockAlbumConverter.convertEntityToModel(any()) }
    }

    @Test
    fun `test getAlbumList`() {
        val albumId = 28
        coEvery { mockAlbumDao.getByAlbumId(any()) } returns listOf(mockAlbumEntity)
        coEvery { mockAlbumConverter.convertEntityToModel(any()) } returns mockAlbum
        //
        val albumList = runBlocking { albumRepository.getAlbumList(albumId) }
        //
        Assert.assertEquals(1, albumList.size)
        coVerify { mockAlbumDao.getByAlbumId(albumId) }
        coVerify { mockAlbumConverter.convertEntityToModel(mockAlbumEntity) }
    }
}