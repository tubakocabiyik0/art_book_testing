package com.example.artbookhilttesting.dependencyInjection

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.artbookhilttesting.R
import com.example.artbookhilttesting.api.RetrofitApi
import com.example.artbookhilttesting.database.ArtDao
import com.example.artbookhilttesting.database.ArtDatabase
import com.example.artbookhilttesting.repo.ArtRepoI
import com.example.artbookhilttesting.repo.ArtRepository
import com.example.artbookhilttesting.util.Util.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectionRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ArtDatabase::class.java, "daoInject").build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofit(): RetrofitApi {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL).build().create(RetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) =
        Glide.with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_background).
                    error(R.drawable.ic_launcher_background)
        )

    @Singleton
    @Provides
    fun injectRealRepo(dao:ArtDao,retrofitApi: RetrofitApi)=ArtRepository(dao,retrofitApi) as ArtRepoI
}