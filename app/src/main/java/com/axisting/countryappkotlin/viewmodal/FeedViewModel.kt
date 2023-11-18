package com.axisting.countryappkotlin.viewmodal

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axisting.countryappkotlin.model.Country
import com.axisting.countryappkotlin.service.CountryAPIService
import com.axisting.countryappkotlin.service.CountryDao
import com.axisting.countryappkotlin.service.CountryDatabase
import com.axisting.countryappkotlin.util.CustomSharedPreferences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class FeedViewModel(application: Application) : BasedViewModel(application) {

    //CountryAPIService Retrofit ve RXJava setlemelerini yaptığımız bir service
    //Single methodunu kullanarak veri alacağız- Geçen sefer call methodunu kullanmıştık

    private val countryApiService = CountryAPIService()
    //disposable = tek kullanımlık, kullan at verimiz. RXjava kütüphanesinden kullanıyoruz
    private val disposable = CompositeDisposable()
    private val customPreferences = CustomSharedPreferences(getApplication())

    private val REFLESH_TIME = 0.1 * 60 * 1000 * 1000* 1000L


    // Bu üçü bizim gözetleyeceğimiz veriler olacak. Canlı olarak bu verileri gözetleyeceğiz.
    // countries -> bize bir list döndürecek ve içeriği Country objesi olacak
    val countries = MutableLiveData<List<Country>>()
    //countryError ve countryLoading'te sadece boolean sonuç almamız yeterli.
    // Error yazısı çıkacak mı, Loading Bar çıkacak mı diye gözetlediğimiz bir livedata olacak
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()
    //Mutable = değişken

    //Fragmentimizde veya viewımizde gözetleyeceğimiz ve çalıştıracağımız asıl fonksiyonumuz
    fun refleshData (){
        val lastTime = customPreferences.getTime()

        if (lastTime!= null && lastTime != 0L && System.nanoTime()-lastTime < REFLESH_TIME) {
           getDataFromSQLite()
        }else {
            getDataFromAPI()
        }


        /*

        //Test amaçlı yapıldı
        val country1 = Country("Turkey", "Asia", "Ankara", "TL" , "Turkish", "www.ss.com")
        val country2 = Country("France", "Europe", "Paris", "EUR" , "French", "www.ss.com")
        val country3 = Country("German", "Europe", "Berlin", "EUR" , "German", "www.ss.com")

        val countryArrayList = arrayListOf<Country>(country1, country2, country3)
        countries.value = countryArrayList
        countryError.value = false
        countryLoading.value = false

         */
    }
    fun refleshDataFromAPI () {
        getDataFromAPI()
    }


    private fun getDataFromSQLite () {
        countryLoading.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()

            showCountries(countries)
            Toast.makeText(getApplication() , "Countries From SQLite" , Toast.LENGTH_SHORT).show()
        }
    }

    //Verileri API'dan getirdiğimiz fonksiyonu ayrı yazmakta fayda var.
    private fun getDataFromAPI () {
        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication() , "Countries From API" , Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value= false
                        e.printStackTrace()

                     }

                }
                )
        )





    }

    private fun showCountries (countryList : List<Country>) {
        countries.value = countryList
        countryLoading.value = false
        countryError.value  = false
    }

    private fun storeInSQLite (list : List<Country>){
        launch {

            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()

            val listLong =dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i++
            }


            showCountries(list)


        }
        customPreferences.saveTime(System.nanoTime())


    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }



}