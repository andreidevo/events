package com.r.events.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.r.events.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.util.*

class ImgZoomDialog(private var imgHref : String) : DialogFragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.dialog_fragment_withimg, null)
        var img = view.findViewById<ImageView>(R.id.imageOp)
        Glide.with(view.context).load(imgHref).into(img)
        getDialog().setCanceledOnTouchOutside(true)

        return view
    }
}