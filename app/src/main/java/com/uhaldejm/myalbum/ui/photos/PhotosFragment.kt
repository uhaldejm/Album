package com.uhaldejm.myalbum.ui.photos

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.uhaldejm.myalbum.R
import com.uhaldejm.myalbum.data.local.entities.PhotoEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.photos_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class PhotosFragment : Fragment(), PhotosAdapter.PhotosAdapterListener {

    @Inject
    lateinit var adapter: PhotosAdapter
    private var listener: PhotosFragmentListener? = null;

    companion object {
        fun newInstance() = PhotosFragment()
    }

    private val viewModel: PhotosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.photos_fragment, container, false)

        return view;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PhotosFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement PhotosFragmentListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadPhotos()
        initializeUI()
    }

    fun loadPhotos() {

        viewModel.photos.observe(viewLifecycleOwner, Observer {
            adapter.setPhotos(it)
        })
        viewModel.error.observe(viewLifecycleOwner, {
            error.isVisible = it
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            rv_container.isRefreshing = it
        })

    }

    fun initializeUI() {
        //Setup Recyclerview
        adapter.initialize(context, this)
        val columns =
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4
        photos_rv.layoutManager = GridLayoutManager(context, columns)
        photos_rv.adapter = adapter

        //Swipe to refresh
        rv_container.setOnRefreshListener { viewModel.refresh() }
    }

    override fun onPhotoClicked(photo: PhotoEntity) {
        listener?.onPhotoClicked(photo)
    }

    interface PhotosFragmentListener {
        fun onPhotoClicked(photo: PhotoEntity)
    }
}