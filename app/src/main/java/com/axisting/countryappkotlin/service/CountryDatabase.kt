package com.axisting.countryappkotlin.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.axisting.countryappkotlin.model.Country

@Database(entities = arrayOf(Country::class) , version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao () : CountryDao

    //Buraya kadar olan kısım Android tutorial'da da var. Database kurmak için minumum gereksinimler
    //abstract class oluştur ve bu RoomDatabase extend alsın
    //@Database () i tanımla
    // entityler bir dizi olarak alabiliriz. bizim sadece 1 tne entitimiz olduğu için sadece onu yazdık
    // daha fazla da olabilirdi sonuçta bu bir database farklı tabloları olabilir
    // versiyon'da da country dao'nun güncellenmesine göre versiyonu da güncellemeliyiz
    //Son olarakta Dao'yu döndüren bir abstract sınıf oluşturduk.

    //componion object uygulamanın tüm scopelarından buraya erişim sağlayabilmemizi sağlıyor
    companion object {
        @Volatile private var instance :CountryDatabase? = null

        private val lock  = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it

            }
        }

        private fun makeDatabase (context : Context) = Room.databaseBuilder(
            context.applicationContext , CountryDatabase::class.java, "countrydatabase"
        ).build()
    }





}