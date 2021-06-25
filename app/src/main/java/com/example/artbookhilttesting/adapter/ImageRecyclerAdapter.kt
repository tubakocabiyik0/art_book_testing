package com.example.artbookhilttesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.artbookhilttesting.R
import com.example.artbookhilttesting.model.Image
import com.example.artbookhilttesting.model.ImageResponse
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(val glide :RequestManager ):RecyclerView.Adapter<ImageRecyclerAdapter.ImageHolder>() {
    class ImageHolder (itemView: View):RecyclerView.ViewHolder(itemView)
    private var onItemClickListener: ((String) -> Unit) ? = null
    private val differUtil=object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem== newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem== newItem
        }

    }

    private var imageRecyclerDiff  =AsyncListDiffer(this,differUtil)
    var imageList : List<String>
    get() =imageRecyclerDiff.currentList
    set(value) = imageRecyclerDiff.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate((R.layout.images_row),parent,false)
        return ImageRecyclerAdapter.ImageHolder(view)
    }

    fun setOnItemClickListener(listener :(String) -> Unit){
        onItemClickListener=listener
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {

        var imageView=holder.itemView.findViewById<ImageView>(R.id.imageView2)
        var searchText=holder.itemView.findViewById<EditText>(R.id.searchText)
        var url = imageList[position]


        holder.itemView.apply {
            glide.load(url).into(imageView)
            setOnClickListener {
                onItemClickListener?.let {
                 it(url)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}