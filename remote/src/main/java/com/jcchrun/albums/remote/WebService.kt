package com.jcchrun.albums.remote

import com.jcchrun.albums.remote.dto.AlbumDto
import retrofit2.http.GET

interface WebService {

    @GET("/img/shared/technical-test.json")
    suspend fun fetchAlbums(): List<AlbumDto>
}