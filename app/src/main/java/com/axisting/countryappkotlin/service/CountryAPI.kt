package com.axisting.countryappkotlin.service

import com.axisting.countryappkotlin.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {


    //API url
    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    //BASE URL ->https://raw.githubusercontent.com/
    //EXT -> atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>


    //burada single yerine observable kullanmıştık bundan önce - Single tek bir defa alıyor veriyi ve
    //sonra tekrar almıyor. Bir ülke verisi çekeceğimiz için bu bizim için okay. ama observable ile
    //devamlı güncel veri çekebilirdik. bu instagram gibi verilerin devamlı güncel olması gereken bir uygulamada bizim için
    //daha iyi olur

    //burada yapacağımız her işlemi belirtebiliriz
    //get post sıralayıp fonksiyonları yazmamız lazım

}