package com.uhaldejm.myalbum.ui.albums

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uhaldejm.myalbum.R
import com.uhaldejm.myalbum.data.local.entities.AlbumEntity
import com.uhaldejm.myalbum.util.StringUtils
import kotlinx.android.synthetic.main.item_album.view.*
import javax.inject.Inject

class AlbumsAdapter @Inject constructor() : RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    private val albums: MutableList<AlbumEntity> = mutableListOf()
    private var context: Context? = null;
    private lateinit var listener: RepoListAdapterListener;

    fun initialize(context: Context?, listener: RepoListAdapterListener) {
        this.context = context;
        this.listener = listener;
    }

    fun setAlbums(albums: List<AlbumEntity>) {
        this.albums.clear()
        this.albums.addAll(albums)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_album, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = StringUtils.capitalizeFirstLetter(albums[position].title)
        holder.view.setOnClickListener { listener.onAlbumClicked(albums[position]) }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.title
        val view = view
    }

    interface RepoListAdapterListener {
        fun onAlbumClicked(album: AlbumEntity)
    }
}