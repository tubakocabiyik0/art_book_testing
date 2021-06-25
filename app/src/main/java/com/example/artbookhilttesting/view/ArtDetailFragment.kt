package com.example.artbookhilttesting.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.artbookhilttesting.R
import com.example.artbookhilttesting.databinding.FragmentArtDetailBinding
import com.example.artbookhilttesting.viewmodel.ViewModelClass
import javax.inject.Inject

class ArtDetailFragment @Inject constructor(val glide:RequestManager ) : Fragment(R.layout.fragment_art_detail) {

    private var fragmentBinding: FragmentArtDetailBinding? = null
    lateinit var viewModel :ViewModelClass

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var binding = FragmentArtDetailBinding.bind(view)
        fragmentBinding = binding
        viewModel=ViewModelProvider(requireActivity()).get(ViewModelClass::class.java)
        subscribeToObserver()
        binding.image.setOnClickListener {
            findNavController().navigate(ArtDetailFragmentDirections.actionArtDetailFragmentToİmageFragment())

        }

        binding.saveButton.setOnClickListener {
          viewModel.addArt(binding.editTextTextPersonName.text.toString(),binding.editTextTextPersonName2.text.toString(),binding.editTextTextPersonName3.text.toString())

        }

        var callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

    private fun subscribeToObserver() {
       viewModel.selectedI.observe(viewLifecycleOwner, Observer {url->
           fragmentBinding?.let {
               glide.load(url).into(it.image)
           }
       })
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }

}