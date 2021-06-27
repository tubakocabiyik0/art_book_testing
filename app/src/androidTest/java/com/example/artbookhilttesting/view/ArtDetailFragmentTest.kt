package com.example.artbookhilttesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.artbookhilttesting.R
import com.example.artbookhilttesting.launchFragmentInHiltContainer
import com.example.artbookhilttesting.model.Image

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


@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ArtDetailFragmentTest {

    @get:Rule
    var hiltRule=HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule=InstantTaskExecutorRule()


    @Inject
    lateinit var artFragmentFactory: ArtFragmentFactory




    @Before
    fun setUp(){
        hiltRule.inject()
    }


    @Test
    fun `ifClickSaveButtonSaveButtonSavedArtToDatabase`(){

        val testViewModel = ViewModelClass(FakeArtRepositoryT())
        launchFragmentInHiltContainer<ArtDetailFragment>(factory = artFragmentFactory){
            viewModel=testViewModel

        }
        Espresso.onView(withId(R.id.artName)).perform(replaceText("tu"))
        Espresso.onView(withId(R.id.artistName)).perform(replaceText("rerere"))
        Espresso.onView(withId(R.id.year)).perform(replaceText("2121"))
        Espresso.onView(withId(R.id.saveButton)).perform(click())

        var list=testViewModel.artList.getOrAwaitValueTest()
        assertThat(list).contains(Image(null,"tu","rerere",2121,""))


    }



    @Test
    fun `ifClickImageGoesToImageFragment`(){

        var navController= Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailFragment>(factory = artFragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.image)).perform(click())
        Mockito.verify(navController).navigate(ArtDetailFragmentDirections.actionArtDetailFragmentToİmageFragment())


    }
    @Test
    fun `ifPressBackArtFragmentOpened`(){

        val navController= Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailFragment>(factory = artFragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()

    }
}