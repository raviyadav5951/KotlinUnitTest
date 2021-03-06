package com.askfortricks.unittest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.askfortricks.unittest.R
import com.askfortricks.unittest.model.Animal
import com.askfortricks.unittest.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * 1.Creating viewmodel Provider.
 * 2.Creating observer(s)
 * 3.Assign the observer
 */

class ListFragment : Fragment(),AnimalListAdapter.OnItemClickListener {
    //creating viewmodel
    private lateinit var viewModel:ListViewModel
    private val listAdapter=AnimalListAdapter( arrayListOf(),this)
    private lateinit var rootView: View

    //Step2 :Creating observer for each and assign in onViewCreated
    private val animalListDataObserver=Observer<List<Animal>>{
        list->list?.let {
        animalList.visibility=View.VISIBLE
        listAdapter.updateAnimalList(list)
    }

    }
    private val loadingLiveDataObserver=Observer<Boolean>{
        isLoading->loadingView.visibility= if(isLoading) View.VISIBLE else View.GONE
        if(isLoading){
            listError.visibility=View.GONE
            animalList.visibility=View.GONE
        }
    }
    private val loadingErrorDataObserver=Observer<Boolean>{
        isError-> listError.visibility=if(isError)View.VISIBLE else View.GONE
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_list, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //creating viewmodel
        viewModel=ViewModelProviders.of(this).get(ListViewModel::class.java)
        //observer assignment
        viewModel.animals.observe(viewLifecycleOwner,animalListDataObserver)
        viewModel.loading.observe(viewLifecycleOwner,loadingLiveDataObserver)
        viewModel.loadError.observe(viewLifecycleOwner,loadingErrorDataObserver)
        viewModel.refresh()

        animalList.apply {
            layoutManager=GridLayoutManager(context,2)
            adapter=listAdapter
        }

        refreshLayout.setOnRefreshListener {
            animalList.visibility=GONE
            listError.visibility= GONE
            loadingView.visibility=View.VISIBLE

            viewModel.forceRefresh()

            refreshLayout.isRefreshing=false
        }
    }

    override fun onItemClicked(animal: Animal) {
        val action=ListFragmentDirections.actionDetail(animal)
        Navigation.findNavController(rootView).navigate(action)
    }
}
