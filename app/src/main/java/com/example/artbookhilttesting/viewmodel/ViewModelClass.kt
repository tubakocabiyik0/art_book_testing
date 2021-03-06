package com.example.artbookhilttesting.viewmodel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artbookhilttesting.model.Image
import com.example.artbookhilttesting.model.ImageResponse
import com.example.artbookhilttesting.repo.ArtRepoI
import com.example.artbookhilttesting.repo.ArtRepository
import com.example.artbookhilttesting.util.Resource
import kotlinx.coroutines.launch

class ViewModelClass @ViewModelInject constructor(private val repository: ArtRepoI) :
    ViewModel() {
    //artview
    var artList = repository.getArt()

    //image search view
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>>
        get() = images

    private  val selectedImage=MutableLiveData<String>()
    val selectedI : LiveData<String>
    get() = selectedImage


    private var insertArtMsg = MutableLiveData<Resource<Image>>()
    val insertArtMessage : LiveData<Resource<Image>>
        get() = insertArtMsg

    //Solving the navigation bug
    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Image>>()

    }

//image detail



    fun setSelectedImage(url:String){
        selectedImage.postValue(url)
    }

    //vşewmodelscope.launc'ı repository i kullanmak için yazıyorum
    fun insertImage(art: Image) =viewModelScope.launch {
        repository.insertArt(art)
    }

    fun addArt(name: String,artistName:String,year:String ){
        if(name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
              insertArtMsg.postValue(Resource.error("Enter name, artist, year", null))
            return

        }
        val yearInt = year.toInt()

        val art = Image(null,name,artistName,yearInt,selectedImage.value?: "")
        insertImage(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun deleteImage(art:Image)=viewModelScope.launch {
        repository.deleteArt(art)
    }


    fun searchForImage(imageName:String) =viewModelScope.launch {
        if(imageName.isEmpty()){

        }
        images.value=Resource.loading(null)
        viewModelScope.launch {
            val response=repository.searchImage(imageName)
            images.value=response

        }



    }


}