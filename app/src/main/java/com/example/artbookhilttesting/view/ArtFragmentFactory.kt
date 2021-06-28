package com.example.artbookhilttesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.artbookhilttesting.adapter.ArtFragmentRecyclerAdapter
import com.example.artbookhilttesting.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artFragmentRecyclerAdapter :  ArtFragmentRecyclerAdapter,
    private val glide:RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
):FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
      return when(className){
       ArtDetailFragment::class.java.name -> ArtDetailFragment(glide)
       ArtFragment::class.java.name -> ArtFragment(artFragmentRecyclerAdapter)
       ImageFragment::class.java.name -> ImageFragment(imageRecyclerAdapter)

          else -> super.instantiate(classLoader, className)
      }


    }
}