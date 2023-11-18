package com.axisting.countryappkotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


//API url
//https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

@Entity(tableName = "country")
data class Country (
                    @ColumnInfo(name = "name")
                    @SerializedName ("name")
                    val countryName : String?,
                    @ColumnInfo(name = "region")
                    @SerializedName("region")
                    val countryRegion : String?,
                    @ColumnInfo(name = "capital")
                    @SerializedName("capital")
                    val countryCapital : String?,
                    @ColumnInfo(name = "currency")
                    @SerializedName("currency")
                    val countryCurrency : String?,
                    @ColumnInfo(name = "language")
                    @SerializedName("language")
                    val countryLanguage : String?,
                    @ColumnInfo(name = "flag")
                    @SerializedName("flag")
                    val countryImageUrl : String?){

    @PrimaryKey(autoGenerate = true)
    var uuid = 0
}