package com.axisting.countryappkotlin.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.axisting.countryappkotlin.R
import com.axisting.countryappkotlin.model.Country
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.downloadImageWithUrl (url : String? , circularProgressDrawable: CircularProgressDrawable ){

    val options = RequestOptions()
        .placeholder(circularProgressDrawable)
        .error(R.drawable.countries)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)



}

fun placeholderProgressBar (context : Context ) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
            strokeWidth = 8f
            centerRadius =  40f
            start()
    }

}
//Burada bir BindAdapter tanımlamış olduk bu sayede artık xml'De imageView içerisinde
//android:downloadUrl kullanabilirim.
@BindingAdapter("android:downloadUrl" )
fun downloadUrl ( view : ImageView, url: String?) {
    view.downloadImageWithUrl(url, placeholderProgressBar(view.context))
}





//Extention örneği -- extention = uzatma ext
/*
fun String.myExtention ( mysting : String){
    println(mysting)
}
*/