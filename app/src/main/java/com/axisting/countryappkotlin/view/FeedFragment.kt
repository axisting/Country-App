package com.axisting.countryappkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.axisting.countryappkotlin.R
import com.axisting.countryappkotlin.adapter.CountryAdapter
import com.axisting.countryappkotlin.model.Country

import com.axisting.countryappkotlin.viewmodal.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

    private lateinit var viewModel : FeedViewModel
    private val countryAdapter =  CountryAdapter(arrayListOf())

class FeedFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView  = inflater.inflate(R.layout.fragment_feed, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refleshData()

        countryListRecyclerView.layoutManager = LinearLayoutManager(context)
        countryListRecyclerView.adapter = countryAdapter

        swipeRefleshLayout.setOnRefreshListener {
            progressBarFromFragmentFeed.visibility = View.VISIBLE
            countryListRecyclerView.visibility= View.GONE
            errorTextFromFragmentFeed.visibility = View.GONE
            viewModel.refleshDataFromAPI()
            swipeRefleshLayout.isRefreshing = false

        }

        observeLiveData()
        /*
        var myString  : String= "babalık"
        myString.myExtention("")

         */



    }
    private fun observeLiveData (){
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                countryListRecyclerView.visibility= View.VISIBLE
                countryAdapter.updateCountry(countries)
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer {error->
            error?.let {
                if (it) {// eğer doğruysa
                    errorTextFromFragmentFeed.visibility = View.VISIBLE
                }else{
                    errorTextFromFragmentFeed.visibility = View.GONE
                }
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it) {// eğer doğruysa
                    progressBarFromFragmentFeed.visibility = View.VISIBLE
                    countryListRecyclerView.visibility= View.GONE
                    errorTextFromFragmentFeed.visibility = View.GONE

                }else{
                    progressBarFromFragmentFeed.visibility = View.GONE
                }
            }
        })

    }


}