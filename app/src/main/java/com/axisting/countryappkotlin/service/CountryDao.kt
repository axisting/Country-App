package com.axisting.countryappkotlin.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.axisting.countryappkotlin.model.Country

@Dao
interface CountryDao {

    //Data Access Object
    // Dao'da istediğimiz SQL komutlarını interface olarak yazıyoruz.
    // Kod içerisinde hata yapma şansımızı azaltıyor
    // Düzenli kod yazmamızı sağlıyor
    // Sistemi oturttuğumuz anda daha kolay ve anlaşılır kod yazmamızı sağlıyor

    @Insert
    suspend fun insertAll (vararg countries : Country) : List<Long>

    // Insert - > INSERT INTO
    // suspend -> coroutine stop & resume
    // vararg -> liste gibi çalışıyor ama istediğimiz kadar country ögesi göndermemize yarayacak
    //Boyut vermeye gerek yok vararg ile - multiple country objects
    // List<Long> -> uuid almak için gerekli - primary keys

    @Query ("SELECT * FROM country")
    suspend fun getAllCountries () : List<Country>

    @Query ("SELECT * FROM country WHERE uuid = :countryID")
    suspend fun getCountry( countryID : Int) : Country

    @Query ("DELETE FROM country")
    suspend fun deleteAllCountries ()
}