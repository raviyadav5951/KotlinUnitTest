package com.askfortricks.unittest.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.palette.graphics.Palette

import com.askfortricks.unittest.R
import com.askfortricks.unittest.model.Animal
import com.askfortricks.unittest.util.getProgressDrawable
import com.askfortricks.unittest.util.loadImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.animalImage
import kotlinx.android.synthetic.main.fragment_detail.animalName
import kotlinx.android.synthetic.main.item_animal.*

class DetailFragment : Fragment() {

    var animal: Animal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //reading the arguments that we received from list fragment item click.
        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        context?.let {
            animalImage.loadImage(getProgressDrawable(it), animal?.imageUrl)
        }
        animalLocation.text = animal?.location
        animalName.text = animal?.name
        animalDiet.text = animal?.diet
        animalLifespan.text = animal?.lifeSpan

        animal?.imageUrl?.let {
            setUpBackgroundColor(it)
        }
    }

    fun setUpBackgroundColor(url: String) {
        url.let {
            Glide.with(this)
                .asBitmap()
                .load(url)
                .into(
                    object : CustomTarget<Bitmap>() {
                        override fun onLoadCleared(placeholder: Drawable?) {

                        }

                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            Palette.from(resource).generate(){
                                palette ->
                                val intColor=palette?.lightMutedSwatch?.rgb?:0
                                animalDetailLayout.setBackgroundColor(intColor)
                            }
                        }

                    }
                )

        }
    }
}
