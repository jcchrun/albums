package com.jcchrun.albums.domain.albums

import com.jcchrun.albums.models.Album
import com.jcchrun.albums.repository.repo.AlbumRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAlbumsByIdUseCaseTest {

    @MockK
    lateinit var mockAlbumRepository: AlbumRepository

    @MockK
    lateinit var mockAlbum: Album

    private lateinit var useCase: GetAlbumsByIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetAlbumsByIdUseCase(mockAlbumRepository)
    }

    @Test
    fun `test run`() {
        val albumId = 123
        coEvery { mockAlbumRepository.getAlbumList(any()) } returns listOf(mockAlbum)
        val result = runBlocking { useCase.run(albumId) }
        Assert.assertEquals(listOf(mockAlbum), result)
        coVerify { mockAlbumRepository.getAlbumList(albumId) }
    }
}