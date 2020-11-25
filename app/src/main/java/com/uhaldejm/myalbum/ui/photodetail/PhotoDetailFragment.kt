package com.uhaldejm.myalbum.ui.photodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.uhaldejm.myalbum.R
import com.uhaldejm.myalbum.util.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.photo_detail_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class PhotoDetailFragment : Fragment() {

    @Inject
    lateinit var picasso: Picasso;

    companion object {
        fun newInstance() = PhotoDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.photo_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadPhoto()
    }

    fun loadPhoto() {

        val args: Bundle? = arguments
        if (args != null) {
            val idInput: Int = args.getInt("id", 0)
            val titleInput: String = args.getString("title", "-")
            val urlInput: String? = args.getString("url", null)

            title.text = StringUtils.capitalizeFirstLetter(titleInput)
            subtitle.text = "ID: ${idInput}"
            picasso.load(urlInput).into(image)
        }

    }

}