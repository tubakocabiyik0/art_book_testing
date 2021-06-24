package com.example.artbookhilttesting.repo

import androidx.lifecycle.LiveData
import com.example.artbookhilttesting.api.RetrofitApi
import com.example.artbookhilttesting.database.ArtDao
import com.example.artbookhilttesting.model.Image
import com.example.artbookhilttesting.model.ImageResponse
import com.example.artbookhilttesting.util.Resource
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import java.lang.reflect.Constructor
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao:ArtDao,
    private val retrofit: RetrofitApi

): ArtRepoI {
    override suspend fun deleteArt(art: Image) {
        artDao.deleteArt(art)
    }

    override suspend fun insertArt(art: Image) {
       artDao.insertArt(art)
    }

    override fun getArt(): LiveData<List<Image>> {
        return artDao.getArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
     return try{
        val response=retrofit.imageSearch(imageString)
         if(response.isSuccessful){
             response.body()?.let {
                 return@let Resource.success(it)
             } ?: Resource.error("error",null)
         }
         else{
           Resource.error("error",null)
         }
     }catch (e:Exception){
        return Resource.error("error",null)
     }




    }
}