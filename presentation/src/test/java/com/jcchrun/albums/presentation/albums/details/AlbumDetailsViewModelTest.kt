package com.jcchrun.albums.presentation.albums.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jcchrun.albums.domain.albums.GetAlbumsByIdUseCase
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.presentation.MainCoroutineScopeRule
import com.jcchrun.albums.repository.repo.AlbumRepository
import com.jcchrun.albums.testutils.extensions.getValueForTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AlbumDetailsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    lateinit var mockAlbumRepository: AlbumRepository

    @MockK
    lateinit var mockAlbum: Album

    lateinit var spyGetAlbumsByIdUseCase: GetAlbumsByIdUseCase

    private lateinit var albumDetailsViewModel: AlbumDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        spyGetAlbumsByIdUseCase = spyk(GetAlbumsByIdUseCase(mockAlbumRepository))
        albumDetailsViewModel = AlbumDetailsViewModel(spyGetAlbumsByIdUseCase)
    }

    @Test
    fun `test getAlbumsRootList`() {
        val albumId = 123
        coEvery { spyGetAlbumsByIdUseCase.run(any()) } returns listOf(mockAlbum)
        albumDetailsViewModel.getAlbumsById(albumId)
        coVerify { spyGetAlbumsByIdUseCase.run(albumId) }
        Assert.assertEquals(listOf(mockAlbum), albumDetailsViewModel.albumsLiveData.getValueForTest())
    }

    @Test
    fun `test cancelRequest`() {
        coEvery { spyGetAlbumsByIdUseCase.cancel() } answers { }
        albumDetailsViewModel.cancelRequest()
        coVerify { spyGetAlbumsByIdUseCase.cancel() }
    }
}