package com.axisting.countryappkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.axisting.countryappkotlin.R
import com.axisting.countryappkotlin.databinding.FragmentCountryBinding
import com.axisting.countryappkotlin.util.downloadImageWithUrl
import com.axisting.countryappkotlin.util.placeholderProgressBar
import com.axisting.countryappkotlin.viewmodal.CountryViewModel
import com.axisting.countryappkotlin.viewmodal.FeedViewModel
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : Fragment() {
    private lateinit var viewModel : CountryViewModel
    private var countryUuid = 0
    private lateinit var  dataBinding : FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //DataBinding methodlrı ile fragmenti inflate ediyoruz.
        dataBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_country , container , false)
        return dataBinding.root //Root dememizin sebebi view ı almak istememiz
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid

        }
        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)


        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {country ->

                dataBinding.country = country

                /*
                countryNameFromCountryFragment.setText(it.countryName)
                capitalNameFromCountryFragment.setText(it.countryCapital)
                currencyNameFromCountryFragment.setText(it.countryCurrency)
                regionNameFromCountryFragment.setText(it.countryRegion)
                languageNameFromCountryFragment.setText(it.countryLanguage)
                context?.let {context->
                    countryImageFromCountryFragment.downloadImageWithUrl(it.countryImageUrl , placeholderProgressBar(context))
                }

                 */

            }
        })

    }




}