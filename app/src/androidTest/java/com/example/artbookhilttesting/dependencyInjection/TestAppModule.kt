package com.example.artbookhilttesting.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.artbookhilttesting.database.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import org.junit.runner.manipulation.Ordering
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context: Context) =
      Room.inMemoryDatabaseBuilder(context,ArtDatabase::class.java)
          .allowMainThreadQueries().build()




}