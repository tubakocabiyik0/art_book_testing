package com.example.artbookhilttesting.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.artbookhilttesting.R
import com.example.artbookhilttesting.databinding.FragmentArtDetailBinding
import javax.inject.Inject

class ArtDetailFragment @Inject constructor(val glide:RequestManager ) : Fragment(R.layout.fragment_art_detail) {

    private var fragmentBinding: FragmentArtDetailBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var binding = FragmentArtDetailBinding.bind(view)
        fragmentBinding = binding
        binding.image.setOnClickListener {
            findNavController().navigate(ArtDetailFragmentDirections.actionArtDetailFragmentToİmageFragment())

        }
        var callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }

}