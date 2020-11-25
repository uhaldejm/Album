package com.uhaldejm.myalbum.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.uhaldejm.myalbum.R
import com.uhaldejm.myalbum.data.local.entities.AlbumEntity
import com.uhaldejm.myalbum.data.local.entities.PhotoEntity
import com.uhaldejm.myalbum.ui.albums.AlbumListFragment
import com.uhaldejm.myalbum.ui.photodetail.PhotoDetailFragment
import com.uhaldejm.myalbum.ui.photos.PhotosFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(),
    AlbumListFragment.AlbumListFragmentListener,
    PhotosFragment.PhotosFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AlbumListFragment.newInstance())
                .commitNow()
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }

    override fun onAlbumClicked(album: AlbumEntity) {
        val frag: PhotosFragment = PhotosFragment.newInstance()
        val args = Bundle()
        args.putInt("albumId", album.id)
        frag.setArguments(args)
        showFragment(frag)
    }

    override fun onPhotoClicked(photo: PhotoEntity) {
        val frag: PhotoDetailFragment = PhotoDetailFragment.newInstance()
        val args = Bundle()
        args.putInt("id", photo.id)
        args.putString("title", photo.title)
        args.putString("url", photo.url)
        frag.setArguments(args)
        showFragment(frag)
    }

}