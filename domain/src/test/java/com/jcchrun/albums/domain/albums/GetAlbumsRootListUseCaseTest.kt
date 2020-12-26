package com.jcchrun.albums.domain.albums

import com.jcchrun.albums.domain.UseCase
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.models.Output
import com.jcchrun.albums.repository.repo.AlbumRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAlbumsRootListUseCaseTest {

    @MockK
    lateinit var mockAlbumRepository: AlbumRepository

    @MockK
    lateinit var mockOutput: Output<List<Album>>

    private lateinit var useCase: GetAlbumsRootListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetAlbumsRootListUseCase(mockAlbumRepository)
    }

    @Test
    fun `test run`() {
        coEvery { mockAlbumRepository.getAlbumRootList() } returns mockOutput
        val result = runBlocking { useCase.run(UseCase.None()) }
        Assert.assertEquals(mockOutput, result)
        coVerify { mockAlbumRepository.getAlbumRootList() }
    }
}