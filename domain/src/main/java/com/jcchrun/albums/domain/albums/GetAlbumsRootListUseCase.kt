package com.jcchrun.albums.domain.albums

import com.jcchrun.albums.domain.UseCase
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.models.Output
import com.jcchrun.albums.repository.repo.AlbumRepository
import javax.inject.Inject

class GetAlbumsRootListUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) : UseCase<UseCase.None, Output<List<Album>>>() {

    override suspend fun run(params: None): Output<List<Album>> {
        return albumRepository.getAlbumRootList()
    }
}