package com.uhaldejm.myalbum.ui.photos

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uhaldejm.myalbum.R
import com.uhaldejm.myalbum.data.local.entities.PhotoEntity
import kotlinx.android.synthetic.main.item_photo.view.*
import javax.inject.Inject


class PhotosAdapter @Inject constructor(private val picasso: Picasso) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val photos: MutableList<PhotoEntity> = mutableListOf()
    private var context: Context? = null;
    private lateinit var listener: PhotosAdapterListener;

    fun initialize(context: Context?, listener: PhotosAdapterListener) {
        this.context = context;
        this.listener = listener;
    }

    fun setPhotos(photos: List<PhotoEntity>) {
        this.photos.clear()
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setOnClickListener { listener.onPhotoClicked(photos[position]) }
        val ctx = context
        if (ctx != null) {
            picasso
                .load(photos[position].thumbnailUrl)
                .placeholder(R.drawable.grey_bg)
                .error(R.drawable.grey_bg)
                .into(holder.image);
        }

    }

    override fun getItemCount(): Int {
        return photos.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view;
        val image: ImageView = view.image;
    }

    interface PhotosAdapterListener {
        fun onPhotoClicked(photo: PhotoEntity)
    }


}