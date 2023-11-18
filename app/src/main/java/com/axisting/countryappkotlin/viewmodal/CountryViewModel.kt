package com.axisting.countryappkotlin.viewmodal

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axisting.countryappkotlin.model.Country
import com.axisting.countryappkotlin.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) :BasedViewModel(application) {

     val countryLiveData = MutableLiveData<Country> ()


    fun getDataFromRoom (uuid : Int){
        //for testing
        //val country1 = Country("Turkey", "Asia", "Ankara", "TL" , "Turkish", "www.ss.com")
        //countryLiveData.value = country1

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }

}