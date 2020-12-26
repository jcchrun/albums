package com.jcchrun.albums.domain.albums

import com.jcchrun.albums.domain.UseCase
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.repository.repo.AlbumRepository
import javax.inject.Inject

class GetAlbumsByIdUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) : UseCase<Int, List<Album>>() {

    override suspend fun run(params: Int): List<Album> {
        return albumRepository.getAlbumList(params)
    }
}