package com.uhaldejm.myalbum.ui.albums

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.uhaldejm.myalbum.data.local.entities.AlbumEntity
import com.uhaldejm.myalbum.data.repositories.definitions.IAlbumsRepository
import com.uhaldejm.myalbum.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumListViewModel @ViewModelInject constructor(
    private val albumsRepository: IAlbumsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _albums: MutableLiveData<List<AlbumEntity>> = MutableLiveData()
    val albums: LiveData<List<AlbumEntity>> = _albums

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<Boolean> = MutableLiveData()
    val error: LiveData<Boolean> = _error

    private var job: Job? = null;

    init {
        init()
    }

    private fun init() {
        killJob()
        job = viewModelScope.launch {
            albumsRepository.getAlbums().collect {
                when (it) {
                    is Resource.Success -> {
                        _error.postValue(false)
                        _loading.postValue(false)
                        _albums.postValue(it.data)
                    }
                    is Resource.Loading -> {
                        _error.postValue(false)
                        _loading.postValue(true)
                    }
                    is Resource.Error -> {
                        _loading.postValue(false)
                        val value = _albums.value
                        if (value == null || value.size < 1) {
                            _error.postValue(true)
                        }
                    }
                }
            }
        }
    }

    fun refresh() {
        this.init()
    }

    private fun killJob() {
        val currentJob = job;
        if (currentJob != null && currentJob.isActive) {
            currentJob.cancel()
        }
    }
}