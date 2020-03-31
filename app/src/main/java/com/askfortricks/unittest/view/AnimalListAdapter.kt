package com.askfortricks.unittest.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.askfortricks.unittest.R
import com.askfortricks.unittest.databinding.ItemAnimalBinding
import com.askfortricks.unittest.model.Animal

//1.Data Binding the row items
//2.Data Binding the function calls(see itemclick listener)

class AnimalListAdapter(val animalList:ArrayList<Animal>,val itemClickListener: OnItemClickListener ):RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<ItemAnimalBinding>(inflater,R.layout.item_animal,parent,false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount()=animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animal=animalList[position]

        //we can also use databinding for click listener but its a developer choice
        /*holder.view.animalLayout.setOnClickListener {
            val action=ListFragmentDirections.actionDetail(animalList[position])
            Navigation.findNavController(holder.itemView).navigate(action)
        }*/
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(animalList[position])
        }
    }

    fun updateAnimalList(newAnimalList:List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    class AnimalViewHolder(var view: ItemAnimalBinding):RecyclerView.ViewHolder(view.root){}

    interface OnItemClickListener{
        fun onItemClicked(animal: Animal)
    }
}