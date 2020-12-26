package com.jcchrun.albums.presentation.albums.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jcchrun.albums.domain.albums.GetAlbumsRootListUseCase
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.models.Output
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
class AlbumRootListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    lateinit var mockAlbumRepository: AlbumRepository

    @MockK
    lateinit var mockOutput: Output<List<Album>>

    lateinit var spyGetAlbumsRootListUseCase: GetAlbumsRootListUseCase

    private lateinit var albumRootListViewModel: AlbumRootListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        spyGetAlbumsRootListUseCase = spyk(GetAlbumsRootListUseCase(mockAlbumRepository))
        albumRootListViewModel = AlbumRootListViewModel(spyGetAlbumsRootListUseCase)
    }

    @Test
    fun `test getAlbumsRootList`() {
        coEvery { spyGetAlbumsRootListUseCase.run(any()) } returns mockOutput
        albumRootListViewModel.getAlbumsRootList()
        coVerify { spyGetAlbumsRootListUseCase.run(any()) }
        Assert.assertEquals(mockOutput, albumRootListViewModel.albumsRootListLiveData.getValueForTest())
    }

    @Test
    fun `test cancelRequest`() {
        coEvery { spyGetAlbumsRootListUseCase.cancel() } answers { }
        albumRootListViewModel.cancelRequest()
        coVerify { spyGetAlbumsRootListUseCase.cancel() }
    }
}