package com.example.artbookhilttesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.example.artbookhilttesting.R
import com.example.artbookhilttesting.adapter.ImageRecyclerAdapter
import com.example.artbookhilttesting.launchFragmentInHiltContainer
import com.example.artbookhilttesting.repo.FakeArtRepositoryT
import com.example.artbookhilttesting.viewmodel.ViewModelClass
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ImageFragmentTest {

    @get:Rule
    var hiltRule=HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()


    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setUp(){
        hiltRule.inject()
    }


    @Test
    fun `selectImageWhenClickImage`(){

        val navController=Mockito.mock(NavController::class.java)
        val testViewModel = ViewModelClass(FakeArtRepositoryT())
        val selectedImageUrl="t.com"
        launchFragmentInHiltContainer<ImageFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
            viewModel=testViewModel
            imageRecyclerAdapter.imageList= listOf(selectedImageUrl)

        }

        Espresso.onView(ViewMatchers.withId(R.id.imagesRecycler)).perform(RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageHolder>(0,
            click()))
        Mockito.verify(navController).popBackStack()
        assertThat(testViewModel.selectedI.getOrAwaitValueTest()).isEqualTo(selectedImageUrl)


    }



}