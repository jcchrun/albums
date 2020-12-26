package com.jcchrun.albums.presentation.albums.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jcchrun.album.core.BaseViewModel
import com.jcchrun.albums.domain.UseCase
import com.jcchrun.albums.domain.albums.GetAlbumsRootListUseCase
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.models.Output

class AlbumRootListViewModel @ViewModelInject constructor(
    private val getAlbumsRootListUseCase: GetAlbumsRootListUseCase
) : BaseViewModel() {

    private val _albumsRootListLiveData = MutableLiveData<Output<List<Album>>>()
    val albumsRootListLiveData: LiveData<Output<List<Album>>> = _albumsRootListLiveData

    fun getAlbumsRootList() {
        getAlbumsRootListUseCase.invoke(UseCase.None()) {
            _albumsRootListLiveData.value = it
        }
    }

    override fun cancelRequest() {
        getAlbumsRootListUseCase.cancel()
    }

}