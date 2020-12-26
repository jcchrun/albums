package com.jcchrun.albums.domain.splash

import com.jcchrun.albums.domain.UseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

class InitApplicationUseCase @Inject constructor() : UseCase<UseCase.None, Boolean>() {

    override suspend fun run(params: None): Boolean {
        delay(1000)
        return true
    }
}