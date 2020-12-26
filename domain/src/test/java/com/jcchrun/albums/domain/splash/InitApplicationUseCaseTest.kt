package com.jcchrun.albums.domain.splash

import com.jcchrun.albums.domain.UseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class InitApplicationUseCaseTest {

    private val useCase = InitApplicationUseCase()

    @Test
    fun `test run`() {
        val result = runBlocking { useCase.run(UseCase.None()) }
        Assert.assertTrue(result)
    }
}