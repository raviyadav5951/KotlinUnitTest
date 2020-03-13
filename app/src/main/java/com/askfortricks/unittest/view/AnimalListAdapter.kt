package com.askfortricks.unittest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.askfortricks.unittest.R
import com.askfortricks.unittest.util.getProgressDrawable
import com.askfortricks.unittest.util.loadImage
import com.askfortricks.unittest.model.Animal
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(val animalList:ArrayList<Animal>):RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.item_animal,parent,false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount()=animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.itemView.animalName.text=animalList[position].name
        holder.itemView.animalImage.loadImage(
            getProgressDrawable(
                holder.itemView.context
            ),animalList[position].imageUrl)
    }

    fun updateAnimalList(newAnimalList:List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    class AnimalViewHolder(view: View):RecyclerView.ViewHolder(view){

    }
}