package com.askfortricks.unittest.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette

import com.askfortricks.unittest.R
import com.askfortricks.unittest.databinding.FragmentDetailBinding
import com.askfortricks.unittest.model.Animal
import com.askfortricks.unittest.model.AnimalPalette
import com.askfortricks.unittest.util.getProgressDrawable
import com.askfortricks.unittest.util.loadImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class DetailFragment : Fragment() {

    var animal: Animal? = null
    private lateinit var dataBinding:FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //reading the arguments that we received from list fragment item click.
        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        //Check utils.kt to load image from server
        //We have used BindingAdapter to declare custom android attribute known as "android:imageurl" for view binding
//        context?.let {
//            dataBinding.animalImage.loadImage(getProgressDrawable(it), animal?.imageUrl)
//        }

        //we have commented this as we have removed id from the fragment_detail.xml and used data binding to bind data
//        animalLocation.text = animal?.location
//        animalName.text = animal?.name
//        animalDiet.text = animal?.diet
//        animalLifespan.text = animal?.lifeSpan

        animal?.imageUrl?.let {
            setUpBackgroundColor(it)
        }

        dataBinding.animal=animal
    }

    private fun setUpBackgroundColor(url: String) {
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
                                dataBinding.palette= AnimalPalette(intColor)
                            }
                        }

                    }
                )

        }
    }
}
