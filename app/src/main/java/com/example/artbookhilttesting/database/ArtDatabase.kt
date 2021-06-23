package com.example.artbookhilttesting.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.artbookhilttesting.model.Image

@Database(entities = [Image::class], version = 1)
abstract class ArtDatabase : RoomDatabase() {

    abstract fun artDao(): ArtDao


}