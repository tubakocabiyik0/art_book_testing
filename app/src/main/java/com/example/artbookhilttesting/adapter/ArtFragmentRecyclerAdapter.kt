package com.example.artbookhilttesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.artbookhilttesting.R
import com.example.artbookhilttesting.model.Image
import javax.inject.Inject

class ArtFragmentRecyclerAdapter @Inject constructor(val glide: RequestManager)
    :RecyclerView.Adapter<ArtFragmentRecyclerAdapter.ArtViewHolder>() {
    class ArtViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val differUtil=object :DiffUtil.ItemCallback<Image>(){
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
           return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
          return oldItem==newItem
        }
    }
    private val recyclerListDiffer=AsyncListDiffer(this,differUtil)
     var artList:List<Image>
        get() =recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
    val view = LayoutInflater.from(parent.context).inflate((R.layout.art_row),parent,false)
       return ArtViewHolder(view)

    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val imageView= holder.itemView.findViewById<ImageView>(R.id.artRow)
        val artName= holder.itemView.findViewById<TextView>(R.id.artName)
        val artistName= holder.itemView.findViewById<TextView>(R.id.artistName)
        val year= holder.itemView.findViewById<TextView>(R.id.year)
        val art=artList[position]

        holder.itemView.apply {
            artName.text="Name: ${art.name}"
            artistName.text="ArtistName: ${art.artistName}"
            year.text="Year: ${art.year}"
            glide.load(art.ImageUrl).into(imageView)

        }

    }

    override fun getItemCount(): Int {
        return artList.size
    }

}