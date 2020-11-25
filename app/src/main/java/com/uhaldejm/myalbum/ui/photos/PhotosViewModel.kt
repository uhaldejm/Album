package com.uhaldejm.myalbum.ui.photos

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.uhaldejm.myalbum.data.local.entities.PhotoEntity
import com.uhaldejm.myalbum.data.repositories.definitions.IPhotosRepository
import com.uhaldejm.myalbum.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PhotosViewModel @ViewModelInject constructor(
    private val photosRepository: IPhotosRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _photos: MutableLiveData<List<PhotoEntity>> = MutableLiveData()
    val photos: LiveData<List<PhotoEntity>> = _photos

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<Boolean> = MutableLiveData()
    val error: LiveData<Boolean> = _error

    private var job: Job? = null;

    private var albumId: Int? = null

    init {
        albumId = savedStateHandle.get<Int>("albumId")
        load()
    }

    private fun load() {
        val id = albumId
        if (id != null) {
            killJob()
            job = viewModelScope.launch {
                photosRepository.getAlbumPhotos(id).collect {
                    when (it) {
                        is Resource.Success -> {
                            _error.postValue(false)
                            _loading.postValue(false)
                            _photos.postValue(it.data)
                        }
                        is Resource.Loading -> {
                            _error.postValue(false)
                            _loading.postValue(true)
                        }
                        is Resource.Error -> {
                            _loading.postValue(false)
                            if (_photos.value == null) {
                                _error.postValue(true)
                            }
                        }
                    }
                }
            }
        } else {
            Log.e("MSJ", "PhotosViewModel:load(). Variable albumId can't be null")
        }

    }

    fun refresh() {
        this.load()
    }

    private fun killJob() {
        val currentJob = job;
        if (currentJob != null && currentJob.isActive) {
            currentJob.cancel()
        }
    }

}