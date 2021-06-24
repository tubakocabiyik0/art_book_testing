package com.example.artbookhilttesting.repo

import android.media.Image
import androidx.lifecycle.LiveData
import com.example.artbookhilttesting.model.ImageResponse
import com.example.artbookhilttesting.util.Resource

interface ArtRepoI {
    suspend fun deleteArt(art: com.example.artbookhilttesting.model.Image)
    suspend fun insertArt(art: com.example.artbookhilttesting.model.Image)
    fun getArt(): LiveData<List<com.example.artbookhilttesting.model.Image>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>


}