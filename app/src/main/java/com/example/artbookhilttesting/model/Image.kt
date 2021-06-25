package com.example.artbookhilttesting.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "arts")
data class Image(
    @PrimaryKey
    var  Id : Int? = null,
    var name: String,
    var artistName: String,
    var year: Int,

    @SerializedName(value = "imageUrl")
    var ImageUrl: String


)