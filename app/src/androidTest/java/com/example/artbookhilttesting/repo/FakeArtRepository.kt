package com.example.artbookhilttesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.artbookhilttesting.model.Image
import com.example.artbookhilttesting.model.ImageResponse
import com.example.artbookhilttesting.util.Resource

class FakeArtRepositoryT :ArtRepoI {
    private val arts = mutableListOf<Image>()
    private val artListLiveData=MutableLiveData<List<Image>>(arts)


    override suspend fun deleteArt(art: Image) {
       arts.add(art)
       refreshDAta()
    }

    override suspend fun insertArt(art: Image) {
       arts.remove(art)
       refreshDAta()
    }

    override fun getArt(): LiveData<List<Image>> {
      return artListLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
       return Resource.success(ImageResponse(0,0, listOf()))
    }
    private fun refreshDAta(){
        artListLiveData.postValue(arts)
    }

}