package com.jcchrun.albums.remote.dto

import com.jcchrun.albums.remote.AbstractRemoteTest
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.junit.Assert
import org.junit.Test

class AlbumDtoTest : AbstractRemoteTest() {

    @Test
    fun `test parse from file`() {
        val jsonString = readFile("json/albums.json")
        val moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java, AlbumDto::class.java)
        val adapter: JsonAdapter<List<AlbumDto>> = moshi.adapter(listType)
        val albumDtoList = adapter.fromJson(jsonString)
        //
        Assert.assertEquals(5000, albumDtoList?.size)
        val firstItem = albumDtoList?.getOrNull(0)
        Assert.assertEquals(1, firstItem?.albumId)
        Assert.assertEquals(1, firstItem?.id)
        Assert.assertEquals("accusamus beatae ad facilis cum similique qui sunt", firstItem?.title)
        Assert.assertEquals("https://via.placeholder.com/600/92c952", firstItem?.url)
        Assert.assertEquals("https://via.placeholder.com/150/92c952", firstItem?.thumbnailUrl)
    }
}