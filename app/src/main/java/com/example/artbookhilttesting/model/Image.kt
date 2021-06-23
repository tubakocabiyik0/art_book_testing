package com.example.artbookhilttesting.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "arts")
data class Image (
    var name :String,
    var artistName : String,
    var year : Integer,
    var imageUrl : String,

    @PrimaryKey(autoGenerate=true)
    var id : Integer


)