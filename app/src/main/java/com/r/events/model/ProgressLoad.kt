package com.r.events.model

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.r.events.R

class ProgressLoad : DialogFragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_fragment, null)
        val image = view.findViewById<ImageView>(R.id.imageGif)
        //ставим гифку
        return view
    }


}
