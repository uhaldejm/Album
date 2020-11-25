package com.uhaldejm.myalbum.ui.albums

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.uhaldejm.myalbum.R
import com.uhaldejm.myalbum.data.local.entities.AlbumEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.album_list_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class AlbumListFragment : Fragment(), AlbumsAdapter.RepoListAdapterListener {

    @Inject
    lateinit var adapter: AlbumsAdapter
    private var listener: AlbumListFragmentListener? = null;

    companion object {
        fun newInstance() = AlbumListFragment()
    }

    private val viewModel: AlbumListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.album_list_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AlbumListFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement AlbumListFragmentListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeUI()
        loadAlbums()
    }

    fun loadAlbums() {
        viewModel.albums.observe(viewLifecycleOwner, Observer {
            adapter.setAlbums(it)
        })
        viewModel.error.observe(viewLifecycleOwner, {
            error.isVisible = it

        })
        viewModel.loading.observe(viewLifecycleOwner, {
            progressBar.isVisible = it;
            rv_container.isRefreshing = it;
        })
    }

    fun initializeUI() {
        //Setup Recyclerview
        adapter.initialize(context, this)
        albums_rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        albums_rv.addItemDecoration(itemDecor)
        albums_rv.adapter = adapter

        //Pull to refresh
        rv_container.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun onAlbumClicked(album: AlbumEntity) {
        listener?.onAlbumClicked(album)
    }

    interface AlbumListFragmentListener {
        fun onAlbumClicked(album: AlbumEntity)
    }

}