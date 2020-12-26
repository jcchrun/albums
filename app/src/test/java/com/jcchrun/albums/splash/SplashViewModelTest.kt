package com.jcchrun.albums.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jcchrun.albums.MainCoroutineScopeRule
import com.jcchrun.albums.domain.splash.InitApplicationUseCase
import com.jcchrun.albums.testutils.extensions.getValueForTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.spyk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SplashViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    lateinit var spyInitApplicationUseCase: InitApplicationUseCase

    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun setup() {
        spyInitApplicationUseCase = spyk(InitApplicationUseCase())
        splashViewModel = SplashViewModel(spyInitApplicationUseCase)
    }

    @Test
    fun `test init`() {
        coEvery { spyInitApplicationUseCase.run(any()) } returns true
        splashViewModel.init()
        coVerify { spyInitApplicationUseCase.run(any()) }
        Assert.assertTrue(splashViewModel.initLiveData.getValueForTest() == true)
    }

    @Test
    fun `test cancelRequest`() {
        coEvery { spyInitApplicationUseCase.cancel() } answers { }
        splashViewModel.cancelRequest()
        coVerify { spyInitApplicationUseCase.cancel() }
    }
}