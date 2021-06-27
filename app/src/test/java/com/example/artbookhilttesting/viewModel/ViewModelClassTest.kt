package com.example.artbookhilttesting.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.artbookhilttesting.MainCoroutineRule
import com.example.artbookhilttesting.getOrAwaitValueTest
import com.example.artbookhilttesting.repo.FakeArtRepository
import com.example.artbookhilttesting.util.Status
import com.example.artbookhilttesting.viewmodel.ViewModelClass
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ViewModelClassTest {

    //bunları main threadde işlem yakmak içindi
    @get:Rule
    var instanceTaskExecutorRule=InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule=MainCoroutineRule()


    private lateinit var viewModelClass: ViewModelClass

    @Before
    fun setUp() {
        // bu benden repo istiyor fakat repo da farklı veriler isteyecek ben bunları vermek yerine fake repo oluşturmalıyım buna test doubles denir
        // viewModelClass = com.example.artbookhilttesting.viewmodel.ViewModelClassTest()
        viewModelClass = ViewModelClass(FakeArtRepository())

    }

    @Test
    fun `insert art without year returns error`() {
        viewModelClass.addArt("name", "artis", "")
        //burası bize livedata veriri bu durum testlerde olmamalı burayı normal data ya çevirmemiz lazım
        //burada livedata olan verimizi normal data ya çeviririz
        val value = viewModelClass.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqcd deualTo(Status.ERROR)

    }

    @Test
    fun `insert art without name returns error`() {
        viewModelClass.addArt("", "artis", "1200")
        //burası bize livedata veriri bu durum testlerde olmamalı burayı normal data ya çevirmemiz lazım
        //burada livedata olan verimizi normal data ya çeviririz
        val value = viewModelClass.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert art without artistName returns error`() {
        viewModelClass.addArt("name", "", "1000")
        //burası bize livedata veriri bu durum testlerde olmamalı burayı normal data ya çevirmemiz lazım
        //burada livedata olan verimizi normal data ya çeviririz
        val value = viewModelClass.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }
}