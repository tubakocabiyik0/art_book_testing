package com.example.artbookhilttesting.view

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.artbookhilttesting.R
import com.example.artbookhilttesting.adapter.ImageRecyclerAdapter
import com.example.artbookhilttesting.databinding.FragmentImageApiBinding
import com.example.artbookhilttesting.model.ImageHits
import com.example.artbookhilttesting.util.Status
import com.example.artbookhilttesting.viewmodel.ViewModelClass
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageFragment @Inject constructor(
     val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {
    private var fragmentBinding: FragmentImageApiBinding? = null
    lateinit var viewModel: ViewModelClass
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         var job : Job? =null
        val binding = FragmentImageApiBinding.bind(view)
         binding.searchText.addTextChangedListener {
             job?.cancel()
             job=lifecycleScope.launch {
                 delay(1000)
                 it?.let {
                     if(it.toString()!=null){
                         viewModel.searchForImage(it.toString())
                     }
                 }
             }
         }

        fragmentBinding = binding
        viewModel = ViewModelProvider(requireActivity()).get(ViewModelClass::class.java)
        subscribeToObserver()
        binding.imagesRecycler.adapter = imageRecyclerAdapter
        //yan yana gösterdiğimiz için
        binding.imagesRecycler.layoutManager = GridLayoutManager(requireContext(), 3)



        //burası tıkladığımızda bize resmin url sini verecek
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    fun subscribeToObserver() {
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    var url = it.data?.hits?.map { imageHits -> imageHits.previewURL }
                    imageRecyclerAdapter.imageList = url ?: listOf()
                    fragmentBinding?.progressCircular?.visibility = View.GONE

                }
                Status.LOADING -> {
                    fragmentBinding?.progressCircular?.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    fragmentBinding?.progressCircular?.visibility = View.GONE
                }

            }


        })

    }
}