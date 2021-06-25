package com.example.artbookhilttesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artbookhilttesting.R
import com.example.artbookhilttesting.adapter.ArtFragmentRecyclerAdapter
import com.example.artbookhilttesting.databinding.FragmentArtsBinding
import com.example.artbookhilttesting.viewmodel.ViewModelClass
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val artRecyclerAdapter : ArtFragmentRecyclerAdapter
) : Fragment(R.layout.fragment_arts) {

    private var fragmentBinding : FragmentArtsBinding?=null
    lateinit var viewModel:ViewModelClass
    private  val swipeCallBack = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
          return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition=viewHolder.layoutPosition
            var selectedArt= artRecyclerAdapter.artList[layoutPosition]
            viewModel.deleteImage(selectedArt)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArtsBinding.bind(view)

        fragmentBinding = binding
        viewModel=ViewModelProvider(requireActivity()).get(ViewModelClass::class.java)
        subscriibetoObservers()

        binding.recyclerViewArt.adapter=artRecyclerAdapter
        binding.recyclerViewArt.layoutManager=LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailFragment())
        }

    }

    private fun subscriibetoObservers(){
       viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.artList=it
       })
    }

    override fun onDestroyView() {
        fragmentBinding=null
        super.onDestroyView()
    }

}