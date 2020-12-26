package com.jcchrun.albums.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jcchrun.album.core.BaseViewModel
import com.jcchrun.albums.domain.UseCase
import com.jcchrun.albums.domain.splash.InitApplicationUseCase

class SplashViewModel @ViewModelInject constructor(
    private val initApplicationUseCase: InitApplicationUseCase
) : BaseViewModel() {

    private val _initLiveData = MutableLiveData<Boolean>()
    val initLiveData: LiveData<Boolean> = _initLiveData

    fun init() {
        initApplicationUseCase.invoke(UseCase.None()) {
            _initLiveData.value = it
        }
    }

    override fun cancelRequest() {
        initApplicationUseCase.cancel()
    }
}