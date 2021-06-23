package com.example.artbookhilttesting.api
import androidx.room.PrimaryKey
import com.example.artbookhilttesting.model.ImageResponse
import com.example.artbookhilttesting.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RefrofitApi {

    @GET("/api/")
    suspend fun imageSearch(
    @Query("q") searchQuery: String,
    @Query("key") apiKey: String = API_KEY

   ):Response<ImageResponse>
}