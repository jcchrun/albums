package com.jcchrun.albums.remote.dto

import com.squareup.moshi.Json

data class AlbumDto(
    @field:Json(name = "id") val id: Int = -1,
    @field:Json(name = "albumId") val albumId: Int = -1,
    @field:Json(name = "title") val title: String? = null,
    @field:Json(name = "url") val url: String? = null,
    @field:Json(name = "thumbnailUrl") val thumbnailUrl: String? = null
) {
}