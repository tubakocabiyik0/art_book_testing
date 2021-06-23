package com.example.artbookhilttesting.database

import androidx.lifecycle.LiveData
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.*
import com.example.artbookhilttesting.model.Image

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(image: Image)

    @Delete
    suspend fun deleteArt(image: Image)

    @Query("SELECT * FROM arts")
    fun getArts(): LiveData<List<Image>>


}

