package com.example.artbookhilttesting.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.artbookhilttesting.model.Image
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ArtDaoTest {
    //burası her şeyi main threadde çalıştırmak istediğimiz için veriliyor bu işe yarıyor
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
     var hiltRule=HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase

    private lateinit var dao: ArtDao


    @Before
    fun setUp() {
        //inmemorybuilder bizim için ramde geçici bir database tutar testlerde bunu kullanmak mantıklıdır
/*       database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArtDatabase::class.java
        ).allowMainThreadQueries().build()
*/
        hiltRule.inject()
        dao = database.artDao()
    }




    @Test
    //run blocing test bizim threadlerimizde birinin işi varken diğerini bekler bu fonklar suspend olduğu için kullandık
    fun insertArtTesting() = runBlockingTest {
        val exampleArt = Image(4, "pizzaTower", "you", 1538, "test.com")
        dao.insertArt(exampleArt)
        //live datayı normal dataya çevirdik
        var list = dao.getArts().getOrAwaitValueTest()
        assertThat(list).contains(exampleArt)


    }

    @Test
    fun insertDeleteTesting() = runBlockingTest {
        var exampleArt = Image(5, "kabir", "hello", 1958, "ted.com")

        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)
        var list = dao.getArts().getOrAwaitValueTest()
        assertThat(list).doesNotContain(exampleArt)
    }
    @After
    fun tearDown() {
        database.close()
    }

}