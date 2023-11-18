package com.axisting.countryappkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation

import androidx.recyclerview.widget.RecyclerView
import com.axisting.countryappkotlin.R
import com.axisting.countryappkotlin.databinding.ItemFeedRecyclerBinding


import com.axisting.countryappkotlin.model.Country
import com.axisting.countryappkotlin.util.downloadImageWithUrl
import com.axisting.countryappkotlin.util.placeholderProgressBar
import com.axisting.countryappkotlin.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_feed_recycler.view.*

class CountryAdapter(val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() , CountryClickListener {

    class CountryViewHolder(val view : ItemFeedRecyclerBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        /*
       val view = inflater.inflate(R.layout.item_feed_recycler, parent, false)
       return CountryViewHolder(view)
*/

        val view = DataBindingUtil.inflate<ItemFeedRecyclerBinding>( inflater,R.layout.item_feed_recycler, parent, false )

        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.view.country = countryList[position]
        holder.view.listener = this

        /*
        holder.view.countryNameItem.text = countryList[position].countryName
        holder.view.countryRegionItem.text = countryList[position].countryRegion

        holder.view.countryImageItemFromRecycler.downloadImageWithUrl(countryList[position].countryImageUrl, placeholderProgressBar(holder.view.context) )

        holder.view.setOnClickListener(){
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

         */

    }

    override fun getItemCount(): Int {
        return countryList.size
    }
    fun updateCountry(newCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(view: View) {
        val uuid = view.stringUuid.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(view).navigate(action)

    }
}