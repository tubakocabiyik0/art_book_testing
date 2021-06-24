package com.example.artbookhilttesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide:RequestManager
):FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
      return when(className){
       ArtDetailFragment::class.java.name -> ArtDetailFragment(glide)

          else -> super.instantiate(classLoader, className)
      }


    }
}