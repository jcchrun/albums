package com.jcchrun.albums.presentation.albums.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jcchrun.album.core.BaseViewModel
import com.jcchrun.albums.domain.albums.GetAlbumsByIdUseCase
import com.jcchrun.albums.models.Album

class AlbumDetailsViewModel @ViewModelInject constructor(
    private val getAlbumsByIdUseCase: GetAlbumsByIdUseCase
) : BaseViewModel() {

    private val _albumsLiveData = MutableLiveData<List<Album>>()
    val albumsLiveData: LiveData<List<Album>> = _albumsLiveData

    fun getAlbumsById(albumId: Int) {
        getAlbumsByIdUseCase.invoke(albumId) {
            _albumsLiveData.value = it
        }
    }

    override fun cancelRequest() {
        getAlbumsByIdUseCase.cancel()
    }

}