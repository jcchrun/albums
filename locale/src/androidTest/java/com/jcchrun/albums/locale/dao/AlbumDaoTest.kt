package com.jcchrun.albums.locale.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.jcchrun.albums.locale.db.AlbumDatabase
import com.jcchrun.albums.locale.entities.AlbumEntityType
import com.jcchrun.albums.locale.entities.AlbumEntity
import kotlinx.coroutines.runBlocking
import org.junit.*

class AlbumDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AlbumDatabase
    private lateinit var albumDao: AlbumDao

    @Before
    fun setupEmptyTable() {
        tearDown()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(
            appContext,
            AlbumDatabase::class.java
        )
            .fallbackToDestructiveMigration()
            .build()
        albumDao = database.albumDao()
    }

    @After
    fun tearDown() {
        if (this::database.isInitialized) {
            database.close()
        }
    }

    @Test
    fun testInsert() {
        val albumList = createAlbumList()
        Assert.assertEquals(
            albumList.size,
            runBlocking {
                albumDao.insertAlbums(albumList)
            }.size
        )
    }

    @Test
    fun testInsertSameCollections() {
        val albumList = createAlbumList()
        Assert.assertEquals(
            albumList.size,
            runBlocking {
                albumDao.insertAlbums(albumList)
            }.size
        )
        Assert.assertEquals(
            albumList.size,
            runBlocking {
                albumDao.insertAlbums(albumList)
            }.size
        )
        Assert.assertEquals(
            albumList.size,
            runBlocking {
                albumDao.getCount()
            }
        )
    }

    @Test
    fun testUpdateAlbumEntityType() {
        val albumList = createAlbumList()
        Assert.assertEquals(
            albumList.size,
            runBlocking {
                albumDao.insertAlbums(albumList)
            }.size
        )
        Assert.assertEquals(
            1,
            runBlocking {
                albumDao.getByAlbumType(AlbumEntityType.ALBUM_ROOT)
            }.size
        )
        runBlocking { albumDao.updateAlbumEntityType(setOf(2, 3), AlbumEntityType.ALBUM_ROOT) }
        Assert.assertEquals(
            3,
            runBlocking {
                albumDao.getByAlbumType(AlbumEntityType.ALBUM_ROOT)
            }.size
        )
    }

    @Test
    fun testDeleteAll() {
        val albumList = createAlbumList()
        Assert.assertEquals(
            albumList.size,
            runBlocking {
                albumDao.insertAlbums(albumList)
            }.size
        )
        Assert.assertEquals(
            albumList.size,
            runBlocking {
                albumDao.getCount()
            }
        )
        Assert.assertEquals(
            0,
            runBlocking {
                albumDao.deleteAll()
                albumDao.getCount()
            }
        )
    }

    @Test
    fun testGetByAlbumTypeFromEmptyTable() {
        Assert.assertEquals(
            0,
            runBlocking {
                albumDao.getByAlbumType(AlbumEntityType.ALBUM_ROOT)
            }.size
        )
    }

    @Test
    fun testGetByAlbumRootType() {
        val albumList = createAlbumList()
        Assert.assertEquals(
            albumList.size,
            runBlocking {
                albumDao.insertAlbums(albumList)
            }.size
        )
        val rootAlbums = runBlocking {
            albumDao.getByAlbumType(AlbumEntityType.ALBUM_ROOT)
        }
        Assert.assertEquals(1, rootAlbums.size)
        Assert.assertEquals(1, rootAlbums[0].id)
    }

    @Test
    fun testGetByAlbumDescendantType() {
        val albumList = createAlbumList()
        Assert.assertEquals(
            albumList.size,
            runBlocking {
                albumDao.insertAlbums(albumList)
            }.size
        )
        val descendantAlbums = runBlocking {
            albumDao.getByAlbumType(AlbumEntityType.ALBUM_DESCENDANT)
        }
        Assert.assertEquals(2, descendantAlbums.size)
        Assert.assertEquals(2, descendantAlbums[0].id)
        Assert.assertEquals(3, descendantAlbums[1].id)
    }

    @Test
    fun testGetByAlbumIdFromEmptyTable() {
        Assert.assertEquals(
            0,
            runBlocking {
                albumDao.getByAlbumId(1)
            }.size
        )
    }

    @Test
    fun testGetByAlbumIdFromMissingId() {
        val albumList = createAlbumList()
        Assert.assertEquals(
            albumList.size,
            runBlocking {
                albumDao.insertAlbums(albumList)
            }.size
        )
        Assert.assertEquals(
            0,
            runBlocking {
                albumDao.getByAlbumId(5)
            }.size
        )
    }

    @Test
    fun testGetByAlbumId() {
        val albumList = createAlbumList()
        Assert.assertEquals(
            albumList.size,
            runBlocking {
                albumDao.insertAlbums(albumList)
            }.size
        )
        Assert.assertEquals(
            3,
            runBlocking {
                albumDao.getByAlbumId(1)
            }.size
        )
    }

    private fun createAlbumList() = listOf(
        AlbumEntity(1, 1, "title 1", "url 1", "thumbnail 1", AlbumEntityType.ALBUM_ROOT),
        AlbumEntity(2, 1, "title 2", "url 2", "thumbnail 2", AlbumEntityType.ALBUM_DESCENDANT),
        AlbumEntity(3, 1, "title 3", "url 3", "thumbnail 3", AlbumEntityType.ALBUM_DESCENDANT)
    )
}